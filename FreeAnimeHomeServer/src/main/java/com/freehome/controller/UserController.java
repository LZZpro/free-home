package com.freehome.controller;

import com.freehome.api.domain.SysUserSecret;
import com.freehome.api.model.UserInfo;
import com.freehome.common.core.domain.R;
import com.freehome.common.core.utils.IpUtils;
import com.freehome.common.core.utils.StringUtils;
import com.freehome.server.IUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Author：Linzz
 * @Describe: 用户登陆相关
 * @Date：2024/8/7 19:02
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    /**
     * 获取当前用户信息
     * @param username
     * @return
     */
    @GetMapping("/info/{username}")
    public R<UserInfo> info(@PathVariable("username") String username)
    {
        SysUserSecret sysUserSecret = userService.getUerInfoByUserName(username);
        if (StringUtils.isNull(sysUserSecret))
        {
            return R.fail("用户名或密码错误");
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setUserid(sysUserSecret.getUserId());
        userInfo.setNickName(sysUserSecret.getUserName());
        userInfo.setIpaddr(IpUtils.getIpAddr());
        userInfo.setUserSecret(sysUserSecret);
        userInfo.setLoginDate(new Date());
        return R.ok(userInfo);
    }

    /**
     * 注册用户信息
     */
  //  @InnerAuth
    @PostMapping("/register")
    public R<Boolean> register(@NotNull @RequestBody SysUserSecret sysUser)
    {
        String username = sysUser.getUserName();
        if (!userService.checkUserNameUnique(sysUser))
        {
            return R.fail("注册失败！账号["+username+"]已被注册");
        }
        return R.ok(userService.registerUser(sysUser));
    }
}
