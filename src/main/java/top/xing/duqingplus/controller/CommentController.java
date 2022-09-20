package top.xing.duqingplus.controller;


import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import top.xing.duqingplus.pojo.Comment;
import top.xing.duqingplus.service.ICommentService;
import top.xing.duqingplus.until.BackInfoUntil;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 文案馆-文章评论 前端控制器
 * </p>
 *
 * @author yxh
 * @since 2022-04-18
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Resource
    private ICommentService commentService;

    @RequestMapping("/addComment")
    public BackInfoUntil<String> upComment(Long uid,Long wid,String content){
         commentService.upComment(uid,wid,content);
         return BackInfoUntil.success();
    }

    @RequestMapping("/delComment")
    public BackInfoUntil<String> delComment(Long id){
        commentService.delComment(id);
        return BackInfoUntil.success();
    }

    @RequestMapping("/getComments")
    public BackInfoUntil<List<Comment>> getComments(Long wid){
        return new BackInfoUntil<List<Comment>>("200","操作成功",commentService.getComments(wid));
    }
}
