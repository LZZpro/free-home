package com.freehome.security.service;

import com.freehome.common.core.constants.SecurityConstants;
import com.freehome.common.core.utils.IpUtils;
import com.freehome.common.core.utils.JwtUtils;
import com.freehome.common.core.utils.StringUtils;
import com.freehome.common.core.uuid.IdUtils;
import com.freehome.api.model.UserInfo;
import com.freehome.redis.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author：Linzz
 * @Describe:
 * @Date：2024/8/8 22:03
 */
@Component
public class TokenService {

    private static final Logger log = LoggerFactory.getLogger(TokenService.class);

    @Autowired
    private RedisService redisService;

    /**
     * 创建令牌
     */
    public Map<String, Object> createToken(UserInfo loginUser)
    {
        String token = IdUtils.fastUUID();
        Long userId = loginUser.getUserSecret().getUserId();
        String userName = loginUser.getUserSecret().getUserName();
        loginUser.setToken(token);
        loginUser.setUserid(userId);
        loginUser.setNickName(userName);
        loginUser.setIpaddr(IpUtils.getIpAddr());
        setTokenCache(loginUser);

        // Jwt存储信息
        Map<String, Object> claimsMap = new HashMap<String, Object>();
        claimsMap.put(SecurityConstants.USER_KEY, token);
        claimsMap.put(SecurityConstants.DETAILS_USER_ID, userId);
        claimsMap.put(SecurityConstants.DETAILS_USERNAME, userName);

        // 接口返回信息
        Map<String, Object> rspMap = new HashMap<String, Object>();
        rspMap.put("access_token", JwtUtils.createToken(claimsMap));
        return rspMap;
    }

    /**
     * 删除用户缓存信息
     */
    public void delLoginUser(String token)
    {
        if (StringUtils.isNotEmpty(token))
        {
            String userKey = JwtUtils.getUserKey(token);
            redisService.deleteObject(getTokenKey(userKey));
        }
    }

    /**
     * 设置令牌缓存
     *
     * @param loginUser 登录信息
     */
    public void setTokenCache(UserInfo loginUser)
    {
        // 根据uuid将loginUser缓存
        String userKey = getTokenKey(loginUser.getToken());
        redisService.setCacheObject(userKey, loginUser);
    }

    private String getTokenKey(String token)
    {
        return "login_tokens:" + token;
    }
}
