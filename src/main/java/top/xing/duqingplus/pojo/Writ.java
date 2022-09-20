package top.xing.duqingplus.pojo;

import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 
 * </p>
 *
 * @author yxh
 * @since 2022-04-18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Writ implements Serializable {

    private Long id;

    private Long uid;

    private String title;

    private User avatar;

    private String content;

    private String type;

    private List<Images> imageList;

    /**
     * 是否审核
     */
    private Integer state;

    private Integer hot;

    private List<Comment> comment;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;


}
