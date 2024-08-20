package com.freehome.security.utils;

import com.freehome.security.service.Snowflake;

import java.io.Serial;
import java.io.Serializable;

/**
 * @Author：Linzz
 * @Describe: 使用雪花算法生成UerId
 * @Date：2024/8/14 23:05
 */
public class IdGeneratorUtil {

     private static Snowflake snowflake = new Snowflake(3,5);
     public static long getId()
     {
          return snowflake.nextId();
     }
}
