package com.freehome.security.utils;

import com.freehome.common.core.constants.SecurityConstants;
import com.freehome.common.core.constants.TokenConstant;
import com.freehome.common.core.context.SecurityContextHolder;
import com.freehome.common.core.utils.ServletUtils;
import com.freehome.common.core.utils.StringUtils;
import com.freehome.api.model.UserInfo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author：Linzz
 * @Describe:
 * @Date：2024/8/8 16:29
 */
public class SecurityUtils {
    /**
     * 获取用户ID
     */
    public static Long getUserId()
    {
        return SecurityContextHolder.getUserId();
    }

    /**
     * 获取用户名称
     */
    public static String getUsername()
    {
        return SecurityContextHolder.getUserName();
    }

    /**
     * 获取用户key
     */
    public static String getUserKey()
    {
        return SecurityContextHolder.getUserKey();
    }

    /**
     * 获取登录用户信息
     */
    public static UserInfo getLoginUser()
    {
        return SecurityContextHolder.get(SecurityConstants.LOGIN_USER, UserInfo.class);
    }

    /**
     * 获取请求token
     */
    public static String getToken()
    {
        return getToken(ServletUtils.getRequest());
    }

    /**
     * 根据request获取请求token
     */
    public static String getToken(HttpServletRequest request)
    {
        // 从header获取token标识
        String token = request.getHeader(TokenConstant.AUTHENTICATION);
        return replaceTokenPrefix(token);
    }

    /**
     * 裁剪token前缀
     */
    public static String replaceTokenPrefix(String token)
    {
        // 如果前端设置了令牌前缀，则裁剪掉前缀
        if (StringUtils.isNotEmpty(token) && token.startsWith(TokenConstant.PREFIX))
        {
            token = token.replaceFirst(TokenConstant.PREFIX, "");
        }
        return token;
    }


    /**
     * 生成BCryptPasswordEncoder密码
     *
     * @param password 密码
     * @return 加密字符串
     */
    public static String encryptPassword(String password)
    {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    /**
     * 判断密码是否相同
     *
     * @param rawPassword 真实密码
     * @param encodedPassword 加密后字符
     * @return 结果
     */
    public static boolean matchesPassword(String rawPassword, String encodedPassword)
    {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
