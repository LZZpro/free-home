package com.freehome.api;

import com.freehome.common.core.constants.SecurityConstants;
import com.freehome.common.core.constants.ServiceNameConstants;
import com.freehome.common.core.domain.R;
import com.freehome.api.domain.SysUserSecret;
import com.freehome.api.factory.RemoteUserFallbackFactory;
import com.freehome.api.model.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

/**
 * @Author：Linzz
 * @Describe: 用户服务
 * @Date：2024/8/7 13:21
 */
@FeignClient(contextId = "remoteUserService", value = ServiceNameConstants.SYSTEM_SERVICE, fallbackFactory = RemoteUserFallbackFactory.class)
public interface RemoteUserService {

    /**
     * 通过用户名查询用户信息
     *
     * @param username 用户名
     * @param source   请求来源
     * @return 结果
     */
    @GetMapping("/user/info/{username}")
    R<UserInfo> getUserInfo(@PathVariable("username") String username, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 注册用户信息
     *
     * @param userSecret 用户信息
     * @param source  请求来源
     * @return 结果
     */
    @PostMapping("/user/register")
    R<Boolean> registerUserInfo(@RequestBody SysUserSecret userSecret, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
