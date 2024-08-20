package com.freehome.config;

import org.apache.hadoop.fs.FileSystem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @Author：Linzz
 * @Describe:
 * @Date：2024/8/18 0:48
 */
@Configuration
public class HdfsConfig {

    @Value("${spring.hadoop.fs-uri}")
    private String hdfsUrl;

    /**
     * 获取HDFS配置信息
     *
     * @return
     */
    @Bean
    public FileSystem fileSystem() throws URISyntaxException, IOException, InterruptedException {
        org.apache.hadoop.conf.Configuration conf = new org.apache.hadoop.conf.Configuration();
        conf.set("fs.defaultFS", hdfsUrl);
        conf.set("dfs.replication","1");
        return FileSystem.get(new URI(hdfsUrl), conf, "root");
    }
}
