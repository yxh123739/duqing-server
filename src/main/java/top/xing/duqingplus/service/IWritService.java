package top.xing.duqingplus.service;

import org.springframework.web.multipart.MultipartFile;
import top.xing.duqingplus.pojo.Favorite;
import top.xing.duqingplus.pojo.Images;
import top.xing.duqingplus.pojo.Writ;
import com.baomidou.mybatisplus.extension.service.IService;
import top.xing.duqingplus.until.BackInfoUntil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yxh
 * @since 2022-04-18
 */
public interface IWritService extends IService<Writ> {
    //上传文案
    BackInfoUntil<String> upload(List<MultipartFile> files, Writ writ, HttpServletRequest request);
    //写入数据
    Long insert(Writ writ);

    List<Writ> selectArchives(Long uid, String type, Integer state);

    List<Images> getImages(Long wid);

    void deleteArchives(Long wid, Long uid);

    void updateArchive(Writ writ,List<MultipartFile> files,HttpServletRequest request);

    void favorite(Favorite favorite);

    void unFavorite(Favorite favorite);

    List<Writ> selectFavorite(Long uid);
}
