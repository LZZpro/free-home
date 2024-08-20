package com.freehome.security.auth;


import com.freehome.common.core.utils.SpringUtils;
import com.freehome.security.service.TokenService;

/**
 * @Author：Linzz
 * @Describe:
 * @Date：2024/8/8 23:02
 */
public class AuthLogic {

    public TokenService tokenService = SpringUtils.getBean(TokenService.class);

    /**
     * 会话注销，根据指定Token
     */
    public void logoutByToken(String token)
    {
        tokenService.delLoginUser(token);
    }

}
