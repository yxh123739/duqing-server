package top.xing.duqingplus.service;

import top.xing.duqingplus.pojo.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 文案馆-文章评论 服务类
 * </p>
 *
 * @author yxh
 * @since 2022-04-18
 */
public interface ICommentService extends IService<Comment> {

    void upComment(Long uid, Long wid, String content);

    void delComment(Long id);

    List<Comment> getComments(Long wid);
}
