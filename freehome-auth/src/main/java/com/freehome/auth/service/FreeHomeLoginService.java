package com.freehome.auth.service;

import com.freehome.api.RemoteUserService;
import com.freehome.common.core.constants.Constants;
import com.freehome.common.core.constants.SecurityConstants;
import com.freehome.common.core.constants.UserConstants;
import com.freehome.common.core.domain.R;
import com.freehome.common.core.enums.UserStatusEnum;
import com.freehome.common.core.exception.ServiceException;
import com.freehome.security.utils.SecurityUtils;
import com.freehome.common.core.utils.StringUtils;
import com.freehome.api.domain.SysUserSecret;
import com.freehome.api.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author：Linzz
 * @Describe:
 * @Date：2024/8/2 18:42
 */
@Component
public class FreeHomeLoginService {


     @Autowired
     private RecordLogService recordLogService;

     @Autowired
     private RemoteUserService remoteUserService;

    @Autowired
     private PassWordService passWordService;
    /**
     * 登陆
     * @return
     */
    public UserInfo login(String userName,String password)
    {
        //// 用户名或密码为空 错误
       if (StringUtils.isAnyBlank(userName,password))
       {
           recordLogService.recordLoginStatusInfo(userName, Constants.LOGIN_FAIL,"用户和密码不能为空");
          throw new ServiceException("用户和密码不能为空");
       }

        // 查询用户信息
        R<UserInfo> userResult = remoteUserService.getUserInfo(userName, SecurityConstants.INNER);

        if (StringUtils.isNull(userResult) || StringUtils.isNull(userResult.getData()))
        {
            recordLogService.recordLoginStatusInfo(userName, Constants.LOGIN_FAIL, "登录用户不存在");
            throw new ServiceException("登录用户：" + userName + " 不存在");
        }

        if (R.FAIL == userResult.getCode())
        {
            throw new ServiceException(userResult.getMsg());
        }

        UserInfo userInfo = userResult.getData();
        SysUserSecret userSecret = userInfo.getUserSecret();
        if (UserStatusEnum.DISABLE.getCode().equals(userInfo.getStatus()))
        {
            recordLogService.recordLoginStatusInfo(userName,Constants.LOGIN_FAIL,"账号已停用,请联系管理员");
            throw new ServiceException("对不起，您的账号：" + userName + " 已停用,请联系客服");
        }

        if (UserStatusEnum.DELETED.getCode().equals(userInfo.getStatus()))
        {
            recordLogService.recordLoginStatusInfo(userName,Constants.LOGIN_FAIL,"账号已被删除");
            throw new ServiceException("对不起，您的账号：" + userName + " 已被删除,请重新注册");
        }

        passWordService.validate(userSecret,password);
        recordLogService.recordLoginStatusInfo(userName, Constants.LOGIN_SUCCESS, "登录成功");
        return userInfo;
    }

    /**
     * 记录登出
     * @param loginName
     */
    public void recordLogout(String loginName)
    {
        recordLogService.recordLoginStatusInfo(loginName, Constants.LOGOUT, "退出成功");
    }

    /**
     * 注册
     */
    public void register(String username, String password)
    {
        // 用户名或密码为空 错误
        if (StringUtils.isAnyBlank(username, password))
        {
            throw new ServiceException("用户/密码必须填写");
        }
        if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH)
        {
            throw new ServiceException("账户长度必须在2到20个字符之间");
        }
        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH)
        {
            throw new ServiceException("密码长度必须在5到20个字符之间");
        }

        // 注册用户信息
        SysUserSecret userSecret = new SysUserSecret();
        userSecret.setUserName(username);
        userSecret.setEncryPassword(SecurityUtils.encryptPassword(password));
        R<?> registerResult = remoteUserService.registerUserInfo(userSecret, SecurityConstants.INNER);

        if (R.FAIL == registerResult.getCode())
        {
            throw new ServiceException(registerResult.getMsg());
        }
        recordLogService.recordLoginStatusInfo(username, Constants.REGISTER, "注册成功");
    }

}
