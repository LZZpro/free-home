package com.freehome.auth.controller;

import com.freehome.auth.form.RegisterBody;
import com.freehome.auth.form.UserLogBody;
import com.freehome.common.core.exception.ServiceException;
import com.freehome.security.auth.AuthUtil;
import com.freehome.auth.service.FreeHomeLoginService;
import com.freehome.common.core.domain.R;
import com.freehome.common.core.utils.JwtUtils;
import com.freehome.security.utils.SecurityUtils;
import com.freehome.common.core.utils.StringUtils;
import com.freehome.api.model.UserInfo;
import com.freehome.security.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author：Linzz
 * @Describe:
 * @Date：2024/8/1 22:56
 */
@RestController
public class TokenController {

    Logger logger = LoggerFactory.getLogger(TokenController.class);

    @Autowired
    private FreeHomeLoginService loginService;

    @Autowired
    private TokenService tokenService;



    @PostMapping("login")
    public R<?> login(@RequestBody UserLogBody form)
    {
        UserInfo userInfo;
        // 用户登录
        try {
             userInfo = loginService.login(form.getUsername(), form.getPassword());
        }catch (ServiceException e){
            logger.error("登陆错误：",e);
            return R.fail(e.getMessage());
        }
        // 获取登录token
        return R.ok(tokenService.createToken(userInfo));
    }

    @DeleteMapping("logout")
    public R<?> logout(HttpServletRequest request)
    {
        String token = SecurityUtils.getToken(request);
        if (StringUtils.isNotEmpty(token))
        {
            String username = JwtUtils.getUserName(token);
            // 删除用户缓存记录
            AuthUtil.logoutByToken(token);
            // 记录用户退出日志
            loginService.recordLogout(username);
        }
        return R.ok();
    }

    /**
     * 用户注册
     * @param registerBody
     * @return
     */
    @PostMapping("register")
    public R<?> register(@RequestBody RegisterBody registerBody)
    {
        loginService.register(registerBody.getUsername(), registerBody.getPassword());
        return R.ok();
    }
}
