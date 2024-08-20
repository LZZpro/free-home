package com.freehome.security.utils;

import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

/**
 * @Author：Linzz
 * @Describe: 视频id生成器
 * @Date：2024/8/19 22:38
 */
public class VideoIdGeneratorUtil {

    private static final String BASE58_ALPHABET = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz";

    private static String generateFVId() {
        // 获取当前时间
        LocalTime now = LocalTime.now();
        // 定义时间格式化器，获取时分秒毫秒部分
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmmssSSS");
        // 格式化当前时间并转换为字符串
        String videoId =  now.format(formatter);
        // 将整数 ID 转换为字节数组
        byte[] videoIdBytes = videoId.getBytes(StandardCharsets.UTF_8);
        // 对字节数组进行Base64编码
        String base64Encoded = Base64.getEncoder().encodeToString(videoIdBytes);
        // 替换Base64中的一些字符，使其更像B站的BV号
        base64Encoded = base64Encoded.replace('+', 'Q')
                .replace('/', 'W')
                .replace('=', 'p');
        // 将字符串映射到Base58字符集（可选步骤）
        StringBuilder bvId = new StringBuilder();
        for (char c : base64Encoded.toCharArray()) {
            bvId.append(BASE58_ALPHABET.charAt(c % BASE58_ALPHABET.length()));
        }
        // 添加FV前缀
        return "FV" + bvId.toString();
    }
    public static String getVideoId() {
         return generateFVId();
    }

}
