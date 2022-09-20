package top.xing.duqingplus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("top.xing.duqingplus.dao")
@SpringBootApplication
public class DuqingPlusApplication {
    public static void main(String[] args) {
        SpringApplication.run(DuqingPlusApplication.class, args);
    }

}
