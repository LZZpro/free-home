package com.freehome;

import org.apache.hadoop.conf.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @Author：Linzz
 * @Describe:
 * @Date：2024/8/1 16:34
 */

@SpringBootApplication
@ComponentScan(basePackages = {"com.freehome","com.freehome.api"})
public class FreeHomeServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FreeHomeServerApplication.class,args);
        System.out.println("++++++++++++Free Home Set up Success++++++++++++");

    }
}
