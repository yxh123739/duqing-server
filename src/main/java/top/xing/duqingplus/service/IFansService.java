package top.xing.duqingplus.service;

import top.xing.duqingplus.pojo.Fans;
import com.baomidou.mybatisplus.extension.service.IService;
import top.xing.duqingplus.until.BackInfoUntil;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 文案馆-用户粉丝 服务类
 * </p>
 *
 * @author yxh
 * @since 2022-04-18
 */
public interface IFansService extends IService<Fans> {

    BackInfoUntil<String> follow(Fans fans);

    BackInfoUntil<String> cancelFollow(Fans fans);

    BackInfoUntil<Map<String, Object>> selectFans(Long uid);
}
