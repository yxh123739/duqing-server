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
 * 文案馆-收藏
 * </p>
 *
 * @author yxh
 * @since 2022-04-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Favorite implements Serializable {

    private Long id;

    private Long uid;

    private Long wid;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;


}
