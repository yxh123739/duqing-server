package top.xing.duqingplus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import top.xing.duqingplus.dao.UserMapper;
import top.xing.duqingplus.data.LocationData;
import top.xing.duqingplus.pojo.User;
import top.xing.duqingplus.service.ISignInService;
import top.xing.duqingplus.service.IUserService;
import top.xing.duqingplus.service.MailService;
import top.xing.duqingplus.until.StringUntil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yxh
 * @since 2022-04-18
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private ISignInService signInService;
    @Resource
    private MailService mailService;

    @Override
    public void register(User user) {
        Assert.isTrue(!StringUntil.isEmpty(user.getQq()), "账号不能为空");
        Assert.isTrue(!StringUntil.isEmpty(user.getPass()), "密码不能为空");
        Assert.isTrue(!StringUntil.isEmpty(user.getNick()), "昵称不能为空");
        Assert.isTrue(StringUntil.isEmpty(user.getPermission()), "不能操作permission属性");
        Assert.isNull(user.getScore(), "不能操作score属性");
        Assert.isNull(selectByQq(user.getQq()), "qq已注册");
        Assert.isNull(selectByName(user.getNick()), "昵称已存在");
        user.setPermission("user");
        user.setBackground("https://p0.ssl.img.360kuai.com/t01aabaa01324d84bef.webp");
        user.setEmail(user.getQq() + "@qq.com");
        int insert = userMapper.insert(user);
        Assert.isTrue(insert == 1, "注册失败");
    }

    @Override
    public User login(User user) {
        Assert.isTrue(!StringUntil.isEmpty(user.getQq()), "账号不能为空");
        Assert.isTrue(!StringUntil.isEmpty(user.getPass()), "密码不能为空");
        Assert.notNull(selectByQq(user.getQq()), "用户不存在");
        User user1 = selectByUP(user.getQq(), user.getPass());
        Assert.notNull(user1, "密码错误");
        return user1;
    }

    @Override
    public List<User> selectAll() {
        List<User> users = userMapper.selectList(null);
        Assert.isTrue(!users.isEmpty(), "没有用户信息");
        return users;
    }

    @Override
    public User selectByUP(String username, String password) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("qq", username)
                .eq("pass", password);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public User selectByName(String name) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("nick", name);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public User selectByEmail(String email) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("email", email);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public User selectByQq(String qq) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("qq", qq);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public void updatePassword(String username, String oldPassword, String newPassword, String confirmPassword) {

    }

    @Override
    public User findPassword(HttpSession session, String code,String email) {
        mailService.judgeCode(session, code,email);
        return selectByEmail(email);
    }

    @Override
    public int changePermission(Long uid, String type) {
        return 0;
    }

    @Override
    public void changeCover(Long uid, MultipartFile image, String type, HttpServletRequest request) {
        Assert.notNull(image, "参数错误");
        saveImage(image, type,uid, request);

    }

    @Override
    public void changeInfo(User user) {
        Assert.notNull(user, "参数错误");
        Assert.notNull(user.getId(), "参数错误");
        int i = userMapper.updateById(user);
        Assert.isTrue(i == 1, "修改失败");
    }

    @Override
    public void signIn(Long uid) {
        signInService.SignIn(uid);
    }

    @Override
    public void sendCode(HttpSession session, String email) {
        Assert.isTrue(!StringUntil.isEmpty(email), "邮箱不能为空");
        Assert.notNull(selectByEmail(email), "邮箱不存在");
        mailService.sendEmail(email,session);
    }

    @Override
    public User getUserById(Long id) {
        User user = userMapper.selectById(id);
        user.setPass("");
        return user;
    }

    private void saveImage(MultipartFile image, String type,Long uid, HttpServletRequest request) {
        String originalFilename = image.getOriginalFilename();
        String filePath = LocationData.getPath() + uid + "/" + type + "/";
        File dest = new File(filePath + originalFilename);
        if (!dest.getParentFile().exists()) {
            boolean mkdirs = dest.getParentFile().mkdirs();
        }
        try {
            image.transferTo(dest);
            User user = new User();
            user.setId(uid);
            if ("background".equals(type)) {
                user.setBackground(LocationData.rePath + uid + "/background/" + originalFilename);
            }else {
                user.setCover(LocationData.rePath + uid + "/cover/" + originalFilename);
            }
            userMapper.updateById(user);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
