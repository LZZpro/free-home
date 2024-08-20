package com.freehome.security.auth;

/**
 * @Author：Linzz
 * @Describe: 权限验证工具类
 * @Date：2024/8/8 23:05
 */
public class AuthUtil {

    /**
     * 底层的 AuthLogic 对象
     */
    public static AuthLogic authLogic = new AuthLogic();



    /**
     * 会话注销，根据指定Token
     *
     * @param token 指定token
     */
    public static void logoutByToken(String token)
    {
        authLogic.logoutByToken(token);
    }

}
