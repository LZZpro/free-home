package com.freehome.security.annotation;

import org.springframework.cloud.openfeign.EnableFeignClients;

import java.lang.annotation.*;

/**
 * @Author：Linzz
 * @Describe: 添加basePackages路径
 * @Date：2024/8/10 23:14
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableFeignClients
public @interface EnableFhFeignClients {

    String[] value() default {};

    String[] basePackages() default { "com.freehome" };

    Class<?>[] basePackageClasses() default {};

    Class<?>[] defaultConfiguration() default {};

    Class<?>[] clients() default {};
}
