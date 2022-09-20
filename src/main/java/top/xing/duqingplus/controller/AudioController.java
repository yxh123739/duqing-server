package top.xing.duqingplus.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import top.xing.duqingplus.pojo.Audio;
import top.xing.duqingplus.service.IAudioService;
import top.xing.duqingplus.until.BackInfoUntil;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yxh
 * @since 2022-04-18
 */
@RestController
@RequestMapping("/audio")
public class AudioController {

    @Resource
    private IAudioService audioService;

    @RequestMapping("/getAudios")
    public BackInfoUntil<List<Audio>> getAudios(){
        List<Audio> audios = audioService.selectList();
        return new BackInfoUntil<>("200", "success", audios);
    }
}
