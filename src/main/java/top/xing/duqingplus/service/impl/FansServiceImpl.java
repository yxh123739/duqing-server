package top.xing.duqingplus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.util.Assert;
import top.xing.duqingplus.dao.UserMapper;
import top.xing.duqingplus.pojo.Fans;
import top.xing.duqingplus.dao.FansMapper;
import top.xing.duqingplus.pojo.User;
import top.xing.duqingplus.service.IFansService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.xing.duqingplus.until.BackInfoUntil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 文案馆-用户粉丝 服务实现类
 * </p>
 *
 * @author yxh
 * @since 2022-04-18
 */
@Service
public class FansServiceImpl extends ServiceImpl<FansMapper, Fans> implements IFansService {

    @Resource
    private FansMapper fansMapper;
    @Resource
    private UserMapper userMapper;
    @Override
    public BackInfoUntil<String> follow(Fans fans) {
        int insert = fansMapper.insert(fans);
        Assert.isTrue(insert == 1, "关注失败");
        User fUser = userMapper.selectById(fans.getUserFollow());
        User user = userMapper.selectById(fans.getUserFans());
        fUser.setFans(fUser.getFans() + 1);
        user.setFollow(user.getFollow() + 1);
        userMapper.updateById(fUser);
        userMapper.updateById(user);
        return BackInfoUntil.success();
    }

    @Override
    public BackInfoUntil<String> cancelFollow(Fans fans) {
        QueryWrapper<Fans> wrapper = new QueryWrapper<>();
        wrapper.eq("user_follow", fans.getUserFollow())
                .eq("user_fans", fans.getUserFans());
        fansMapper.delete(wrapper);
        User fUser = userMapper.selectById(fans.getUserFollow());
        User user = userMapper.selectById(fans.getUserFans());
        fUser.setFans(fUser.getFans() - 1);
        user.setFollow(user.getFollow() - 1);
        userMapper.updateById(fUser);
        userMapper.updateById(user);
        return BackInfoUntil.success();
    }

    @Override
    public BackInfoUntil<Map<String, Object>> selectFans(Long uid) {
        Map<String, Object> map = new HashMap<>();
        List<User> mFans = new ArrayList<>();
        List<User> mFollows = new ArrayList<>();
        Assert.notNull(uid, "用户id不能为空");
        List<Fans> fans = this.list(new QueryWrapper<Fans>().eq("user_follow", uid));
        List<Fans> follows = this.list(new QueryWrapper<Fans>().eq("user_fans", uid));
        for (Fans fan : fans) {
            mFans.add(userMapper.selectById(fan.getUserFans()));
        }
        for (Fans follow : follows) {
            mFollows.add(userMapper.selectById(follow.getUserFollow()));
        }
        map.put("fans", mFans);
        map.put("follows", mFollows);
        return new BackInfoUntil<>("200", "查询成功", map);
    }


}
