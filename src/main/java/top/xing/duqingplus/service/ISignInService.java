package top.xing.duqingplus.service;

import top.xing.duqingplus.pojo.SignIn;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户签到表 服务类
 * </p>
 *
 * @author yxh
 * @since 2022-04-19
 */
public interface ISignInService extends IService<SignIn> {

    void SignIn(Long uid);

}
