package top.xing.duqingplus.controller;


import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.xing.duqingplus.pojo.Favorite;
import top.xing.duqingplus.pojo.Writ;
import top.xing.duqingplus.service.IWritService;
import top.xing.duqingplus.until.BackInfoUntil;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
@RequestMapping("/writ")
public class WritController {
    @Resource
    private IWritService writService;

    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public BackInfoUntil<String> upload(@RequestParam("file[]") List<MultipartFile> files, Writ writ, HttpServletRequest request){
        return writService.upload(files, writ,request);
    }

    @RequestMapping("/archives")
    public BackInfoUntil<List<Writ>> selectArchives(Long uid,String type,Integer state){
        List<Writ> writs = writService.selectArchives(uid,type,state);
        return new BackInfoUntil<>("200","操作成功",writs);
    }

    @RequestMapping("/delete")
    public BackInfoUntil<String> deleteArchives(Long wid, Long uid){
        writService.deleteArchives(wid,uid);
        return BackInfoUntil.success();
    }

    @PostMapping("/update")
    public BackInfoUntil<String> updateArchives(@RequestParam("file[]") List<MultipartFile> files,Writ writ,HttpServletRequest request){
        writService.updateArchive(writ,files,request);
        return BackInfoUntil.success();
    }

    @RequestMapping("/favorite")
    public BackInfoUntil<String> favorite(Favorite favorite){
        writService.favorite(favorite);
        return BackInfoUntil.success();
    }

    @RequestMapping("/unFavorite")
    public BackInfoUntil<String> unFavorite(Favorite favorite){
        writService.unFavorite(favorite);
        return BackInfoUntil.success();
    }

    @RequestMapping("/selectFavorite")
    public BackInfoUntil<List<Writ>> selectFavorite(Long uid){
        List<Writ> writs = writService.selectFavorite(uid);
        return new BackInfoUntil<>("200","操作成功",writs);
    }
}
