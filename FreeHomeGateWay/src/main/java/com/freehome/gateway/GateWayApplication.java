package com.freehome.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @Author：Linzz
 * @Describe:
 * @Date：2024/8/1 17:10
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class GateWayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GateWayApplication.class,args);
        System.out.println("++++++++++++网关启动成功++++++++++++");
    }
}
