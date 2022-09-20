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
 * 用户签到表
 * </p>
 *
 * @author yxh
 * @since 2022-04-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SignIn implements Serializable {

    private Long id;

    /**
     * 签到用户id
     */
    private Long uid;

    /**
     * 连续签到天数
     */
    private Integer continueDays;

    /**
     * 更新日期, 最后签到日期
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    public SignIn(Long uid) {
        this.uid = uid;
    }
}
