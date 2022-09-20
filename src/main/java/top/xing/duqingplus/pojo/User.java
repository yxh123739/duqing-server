package top.xing.duqingplus.pojo;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
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
public class User implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 账号
     */
    private String qq;

    /**
     * 邮箱
     */
    private String email;

    private String pass;

    private String nick;

    /**
     * 权限
     */
    private String permission;

    /**
     * 积分
     */
    private Integer score;

    private Integer fans;

    private Integer follow;

    private Long experience;

    /**
     * 个性签名
     */
    private String sign;

    private String cover;

    private String background;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 最后更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;


}
