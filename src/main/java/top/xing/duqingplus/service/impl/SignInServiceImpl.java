package top.xing.duqingplus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.util.Assert;
import top.xing.duqingplus.dao.UserMapper;
import top.xing.duqingplus.pojo.SignIn;
import top.xing.duqingplus.dao.SignInMapper;
import top.xing.duqingplus.pojo.User;
import top.xing.duqingplus.service.ISignInService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * <p>
 * 用户签到表 服务实现类
 * </p>
 *
 * @author yxh
 * @since 2022-04-19
 */
@Service
public class SignInServiceImpl extends ServiceImpl<SignInMapper, SignIn> implements ISignInService {

    @Resource
    private SignInMapper signInMapper;
    @Resource
    private UserMapper userMapper;
    @Override
    public void SignIn(Long uid) {
        // 查询用户是否签过到
        QueryWrapper<SignIn> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SignIn::getUid, uid);
        SignIn signIn = signInMapper.selectOne(queryWrapper);
        /*没有签过到, 直接新增*/
        if (null == signIn) {
            int insert = signInMapper.insert(new SignIn(uid));
            Assert.isTrue(insert == 1, "签到失败");
        } else {/*签过到*/
            // 判断最后签到日期与当前日期是否超过一天
            LocalDate signInTime = signIn.getUpdateTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate currTime = LocalDate.now();
            long daysDiff = ChronoUnit.DAYS.between(signInTime, currTime);
            if (daysDiff <= 0) {
               throw new IllegalArgumentException("您今天已经签到过了");
            }
            if (daysDiff > 1) {
                // 1, 超过一天, 把连续签到的天数重置为 1
                signIn.setContinueDays(1);
            } else {
                // 2, 没有超过一天, 把连续签到的天数+1
                signIn.setContinueDays(signIn.getContinueDays() + 1);
            }
            int i = signInMapper.updateById(signIn);
            Assert.isTrue(i == 1, "签到失败");
        }
        User user = userMapper.selectById(uid);
        user.setExperience(user.getExperience() + 20);
        user.setScore(user.getScore() + 5);
        userMapper.updateById(user);
    }
}
