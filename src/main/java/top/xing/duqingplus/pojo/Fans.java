package top.xing.duqingplus.pojo;

import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 文案馆-用户粉丝
 * </p>
 *
 * @author yxh
 * @since 2022-04-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Fans implements Serializable {

    private Long id;

    /**
     * 被关注者用户id
     */
    private Long userFollow;

    /**
     * 粉丝id，关注者的id
     */
    private Long userFans;

    /**
     * 关注时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;


}
