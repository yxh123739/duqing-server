package top.xing.duqingplus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.web.multipart.MultipartFile;
import top.xing.duqingplus.dao.*;
import top.xing.duqingplus.data.LocationData;
import top.xing.duqingplus.pojo.*;
import top.xing.duqingplus.service.IWritService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.xing.duqingplus.until.BackInfoUntil;
import top.xing.duqingplus.until.StringUntil;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yxh
 * @since 2022-04-18
 */
@Service
public class WritServiceImpl extends ServiceImpl<WritMapper, Writ> implements IWritService {

    private static final Integer AUDIT = 1;//1代表无需审核，0为需要
    @Resource
    private WritMapper writMapper;
    @Resource
    private ImagesMapper imageMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private CommentMapper commentMapper;
    @Resource
    private FavoriteMapper favoriteMapper;



    @Override
    public BackInfoUntil<String> upload(List<MultipartFile> files, Writ writ, HttpServletRequest request) {
        String reqPath = LocationData.rePath;
            Assert.isTrue(!StringUntil.isEmpty(writ.getType()), "类型为必填参数");
        if (files.isEmpty()) {
            return new BackInfoUntil<>("400", "请选择文件", null);
        }
        writ.setState(AUDIT);
        Long wid = this.insert(writ);
        makeDir(files,writ.getUid(), wid,reqPath);
        return new BackInfoUntil<>("200", "发布成功", "info");
    }

    @Override
    public Long insert(Writ writ) {
        int insert = writMapper.insert(writ);
        Assert.isTrue(insert == 1, "上传失败");
        return writ.getId();
    }

    @Override
    public List<Writ> selectArchives(Long uid, String type, Integer state) {
        List<Writ> writs;
        List<Images> images;
        QueryWrapper<Writ> wrapper = new QueryWrapper<>();
        if (uid==null){
            wrapper.eq("state", state)
                    .eq("type", type);
        } else {
            wrapper.eq("state", state)
                    .eq("type", type)
                    .eq("uid", uid);
        }
        writs = writMapper.selectList(wrapper);
        Assert.isTrue(!writs.isEmpty(), "查询失败或未添加数据");
        for (Writ writ : writs) {
            images = this.getImages(writ.getId());
            writ.setImageList(images);
            writ.setAvatar(userMapper.selectById(writ.getUid()));
            List<Comment> comments = commentMapper.selectList(new QueryWrapper<Comment>().eq("wid", writ.getId()));
            for (Comment comment : comments) {
                comment.setUinfo(userMapper.selectById(comment.getUid()));
            }
            writ.setComment(comments);
        }
        return writs;
    }


    @Override
    public List<Images> getImages(Long wid) {
        QueryWrapper<Images> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("wid", wid);
        List<Images> images = imageMapper.selectList(queryWrapper);
        Assert.isTrue(!images.isEmpty(), "查询失败或没有数据");
        return images;
    }

    @Override
    public void deleteArchives(Long wid, Long uid) {
        Assert.isTrue(wid!=null &&uid != null, "参数不能为空");
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("id", uid)
                .eq("permission", "admin");
        User user = userMapper.selectOne(wrapper);
        Writ writ = writMapper.selectById(wid);
        if (user == null) {
            Assert.isTrue(writ.getUid().equals(uid), "权限不足，无法操作");
        }
        int i = writMapper.deleteById(wid);
        imageMapper.delete(new QueryWrapper<Images>().eq("wid",wid));
        commentMapper.delete(new QueryWrapper<Comment>().eq("wid",wid));
        deleteFile(LocationData.getPath()+writ.getUid()+"/"+wid);
        Assert.isTrue(i == 1, "删除失败");
    }

    @Override
    public void updateArchive(Writ writ,List<MultipartFile> files,HttpServletRequest request) {
        Long wid = writ.getId();
        String reqPath = LocationData.rePath;
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("id", writ.getUid())
                .eq("permission", "admin");
        User user = userMapper.selectOne(wrapper);
        Writ writ1 = writMapper.selectById(wid);
        if (user == null) {
            Assert.isTrue(writ.getUid().equals(writ1.getUid()), "权限不足，无法操作");
        }
        writ.setUid(writ1.getUid());
        writ.setState(AUDIT);
        int i = writMapper.updateById(writ);
        Assert.isTrue(i == 1, "修改失败");
        imageMapper.delete(new QueryWrapper<Images>().eq("wid",wid));
        deleteFile(LocationData.getPath()+writ1.getUid()+"/"+wid);
        makeDir(files,writ1.getUid(),wid,reqPath);
    }

    @Override
    public void favorite(Favorite favorite) {
        Assert.isTrue(favorite.getUid()!=null && favorite.getWid()!=null, "参数不能为空");
        int insert = favoriteMapper.insert(favorite);
        Assert.isTrue(insert == 1, "收藏失败");
    }

    @Override
    public void unFavorite(Favorite favorite) {
        Assert.isTrue(favorite.getUid()!=null && favorite.getWid()!=null, "参数不能为空");
        int i = favoriteMapper.delete(new QueryWrapper<Favorite>().eq("uid", favorite.getUid()).eq("wid", favorite.getWid()));
        Assert.isTrue(i == 1, "取消收藏失败");
    }

    @Override
    public List<Writ> selectFavorite(Long uid) {
        Assert.isTrue(uid!=null, "参数不能为空");
        List<Favorite> fs = favoriteMapper.selectList(new QueryWrapper<Favorite>().eq("uid", uid));
        List<Writ> writs = new ArrayList<>();
        for (Favorite favorite : fs) {
            Writ writ = writMapper.selectById(favorite.getWid());
            writ.setImageList(getImages(writ.getId()));
            writ.setAvatar(userMapper.selectById(writ.getUid()));
            List<Comment> comments = commentMapper.selectList(new QueryWrapper<Comment>().eq("wid", writ.getId()));
            for (Comment comment : comments) {
                comment.setUinfo(userMapper.selectById(comment.getUid()));
            }
            writ.setComment(comments);
            writs.add(writ);
        }
        if (writs.size() == 0) {
            throw new IllegalArgumentException("没有收藏的文章");
        }
        return writs;
    }


    private static void deleteFile(String path){
        File file = new File(path);
        if (!file.exists()){
            return;
        }
        File[] files = file.listFiles();
        assert files != null;
        for (File f: files){
            boolean delete = f.delete();
            Assert.isTrue(delete,"删除文件失败");
        }
        boolean delete = file.delete();
        Assert.isTrue(delete,"删除文件失败");
    }

    private  void makeDir(@NotNull List<MultipartFile> files, Long uid, Long wid, String reqPath){
        Images images = new Images();
        for (MultipartFile file : files) {
            String originalFilename = file.getOriginalFilename();
            String filePath = LocationData.getPath() + uid + "/" + wid + "/";
            File dest = new File(filePath + originalFilename);
            if (!dest.getParentFile().exists()) {
                boolean mkdirs = dest.getParentFile().mkdirs();
            }
            try {
                file.transferTo(dest);
                images.setWid(wid);
                images.setImage(reqPath + uid + "/"+wid+"/" + originalFilename);
                int insert = imageMapper.insert(images);
                if (insert != 1) {
                    throw new IllegalArgumentException("文件写入失败");
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new IllegalArgumentException("写入数据失败");
            }
        }
    }

}
