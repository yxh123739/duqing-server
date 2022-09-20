package top.xing.duqingplus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.util.Assert;
import top.xing.duqingplus.dao.UserMapper;
import top.xing.duqingplus.pojo.Comment;
import top.xing.duqingplus.dao.CommentMapper;
import top.xing.duqingplus.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 文案馆-文章评论 服务实现类
 * </p>
 *
 * @author yxh
 * @since 2022-04-18
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {
    @Resource
    private UserMapper userMapper;

    @Override
    public void upComment(Long uid, Long wid, String content) {
        Assert.notNull(uid, "用户id不能为空");
        Assert.notNull(wid, "文章id不能为空");
        Assert.notNull(content, "评论内容不能为空");
        Comment comment = new Comment();
        comment.setUid(uid);
        comment.setWid(wid);
        comment.setContent(content);
        this.save(comment);
    }

    @Override
    public void delComment(Long id) {
        Assert.notNull(id, "评论id不能为空");
        this.removeById(id);
    }

    @Override
    public List<Comment> getComments(Long wid) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("wid", wid);
        List<Comment> comments = this.list(wrapper);
        for (Comment comment : comments) {
            comment.setUinfo(userMapper.selectById(comment.getUid()));
        }
        Assert.notNull(comments, "还没有评论哦");
        return comments;
    }
}
