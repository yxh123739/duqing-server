package top.xing.duqingplus.service.impl;

import org.springframework.util.Assert;
import top.xing.duqingplus.pojo.Audio;
import top.xing.duqingplus.dao.AudioMapper;
import top.xing.duqingplus.service.IAudioService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yxh
 * @since 2022-04-18
 */
@Service
public class AudioServiceImpl extends ServiceImpl<AudioMapper, Audio> implements IAudioService {

    @Resource
    private AudioMapper audioMapper;
    @Override
    public List<Audio> selectList() {
        List<Audio> audio = audioMapper.selectList(null);
        Assert.notNull(audio,"查询失败或没有数据");
        return audio;
    }
}
