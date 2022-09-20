package top.xing.duqingplus.pojo;

import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 文案馆-文章评论
 * </p>
 *
 * @author yxh
 * @since 2022-04-18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Comment implements Serializable {

    private Long id;

    private Long wid;

    private Long uid;

    private User uinfo;

    /**
     * 评论回复内容
     */
    private String content;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;


}
