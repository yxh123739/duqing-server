package top.xing.duqingplus.data;

import org.springframework.boot.system.ApplicationHome;
import org.springframework.util.ClassUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Objects;

public class LocationData {
    //项目静态资源保存路径
    public static final String path = Objects.requireNonNull(Objects.requireNonNull(ClassUtils.getDefaultClassLoader()).getResource("")).getPath() + "static/";


    ///域名
    public static String rePath = "https://duqing.yuxinghe.top/static/";

    //获取项目路径
    public static String getPath() {
        ApplicationHome h = new ApplicationHome(LocationData.class);
        File jarF = h.getSource();
        System.out.println(jarF.getParentFile().toString());
        return jarF.getParentFile().toString()+"/static/";
    }

}
