package top.xing.duqingplus.dao;

import org.springframework.stereotype.Repository;
import top.xing.duqingplus.pojo.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 文案馆-文章评论 Mapper 接口
 * </p>
 *
 * @author yxh
 * @since 2022-04-18
 */
@Repository
public interface CommentMapper extends BaseMapper<Comment> {

}
