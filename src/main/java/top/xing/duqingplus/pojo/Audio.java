package top.xing.duqingplus.pojo;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author yxh
 * @since 2022-04-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Audio implements Serializable {

    private Long id;

    private Long uid;

    /**
     * 姓名
     */
    private String name;

    private String artist;

    /**
     * 邮箱
     */
    private String image;

    private String url;


}
