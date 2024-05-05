package cn.machaojin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author Ma Chaojin
 */
@SpringBootApplication
public class CodeIntegrationApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(CodeIntegrationApplication.class, args);
        System.out.println("启动成功！！！！");
    }

}
