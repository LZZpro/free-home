package com.freehome.auth.service;

import com.freehome.common.core.constants.CacheConstant;
import com.freehome.common.core.constants.Constants;
import com.freehome.common.core.exception.ServiceException;
import com.freehome.redis.service.RedisService;
import com.freehome.security.utils.SecurityUtils;
import com.freehome.api.domain.SysUserSecret;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Author：Linzz
 * @Describe: 密码管理
 * @Date：2024/8/8 15:26
 */
@Component
public class PassWordService {

    @Autowired
    private RedisService redisService;

    @Autowired
    private RecordLogService recordLogService;

    /**
     * 登录账户密码错误次数缓存键名
     *
     * @param username 用户名
     * @return 缓存键key
     */
    private String getCacheKey(String username)
    {
        return CacheConstant.PWD_ERR_CNT_KEY + username;
    }

    public void validate(SysUserSecret user, String password)
    {
        String username = user.getUserName();

        Integer retryCount = redisService.getCacheObject(getCacheKey(username));

        if (retryCount == null)
        {
            retryCount = 0;
        }

        if (retryCount >= CacheConstant.PASSWORD_MAX_RETRY_COUNT)
        {
            String errMsg = String.format("密码输入错误%s次，帐户锁定%s分钟",
                    CacheConstant.PASSWORD_MAX_RETRY_COUNT, CacheConstant.PASSWORD_LOCK_TIME);
            recordLogService.recordLoginStatusInfo(username, Constants.LOGIN_FAIL,errMsg);
            throw new ServiceException(errMsg);
        }

        if (!matches(user, password))
        {
            retryCount = retryCount + 1;
            recordLogService.recordLoginStatusInfo(username, Constants.LOGIN_FAIL, String.format("密码输入错误%s次", retryCount));
            redisService.setCacheObject(getCacheKey(username), retryCount, CacheConstant.PASSWORD_LOCK_TIME, TimeUnit.MINUTES);
            throw new ServiceException("用户不存在/密码错误");
        }
        else
        {
            clearLoginRecordCache(username);
        }
    }

    public boolean matches(SysUserSecret user, String rawPassword)
    {
        return SecurityUtils.matchesPassword(rawPassword, user.getEncryPassword());
    }

    public void clearLoginRecordCache(String loginName)
    {
        if (redisService.hasKey(getCacheKey(loginName)))
        {
            redisService.deleteObject(getCacheKey(loginName));
        }
    }

}
