package top.xing.duqingplus.service;

import top.xing.duqingplus.pojo.Audio;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yxh
 * @since 2022-04-18
 */
public interface IAudioService extends IService<Audio> {

    List<Audio> selectList();
}
