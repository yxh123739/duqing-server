package top.xing.duqingplus.controller;


import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import top.xing.duqingplus.pojo.Fans;
import top.xing.duqingplus.service.IFansService;
import top.xing.duqingplus.until.BackInfoUntil;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 文案馆-用户粉丝 前端控制器
 * </p>
 *
 * @author yxh
 * @since 2022-04-18
 */
@RestController
@RequestMapping("/fans")
public class FansController {
    @Resource
    private IFansService fansService;

    @RequestMapping("/follow")
    public BackInfoUntil<String> follow(Fans fans){
        return fansService.follow(fans);
    }

    @RequestMapping("/cancelFollow")
    public BackInfoUntil<String> cancelFollow(Fans fans){
        return fansService.cancelFollow(fans);
    }

    @RequestMapping("/followList")
    public BackInfoUntil<Map<String,Object>> selectFans(Long uid){
        return fansService.selectFans(uid);
    }
}
