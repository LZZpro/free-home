package com.freehome.auth;

import com.freehome.security.annotation.EnableFhFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @Author：Linzz
 * @Describe:
 * @Date：2024/8/1 22:46
 */
@EnableFhFeignClients
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class,args);
        System.out.println("++++++++++++用户认证启动成功++++++++++++");
    }
}
