package com.freehome.auth.service;

import com.freehome.api.RemoteLogService;
import com.freehome.common.core.constants.Constants;
import com.freehome.common.core.constants.SecurityConstants;
import com.freehome.common.core.utils.IpUtils;
import com.freehome.common.core.utils.StringUtils;
import com.freehome.api.domain.SysLoginRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author：Linzz
 * @Describe: 记录日志
 * @Date：2024/8/6 16:25
 */
@Component
public class RecordLogService {

    @Autowired(required = false)
    private RemoteLogService remoteLogService;

    /**
     *记录登录信息
     * @param username 用户名
     * @param status 状态
     * @param message 信息
     */
    public void recordLoginStatusInfo(String username, String status, String message)
    {
        SysLoginRecord loginRecord = new SysLoginRecord();

        loginRecord.setUserName(username);
        loginRecord.setIpaddr(IpUtils.getIpAddr());
        loginRecord.setMsg(message);
        loginRecord.setAccessTime(new Date());

       // 日志状态
        if (StringUtils.equalsAny(status, Constants.LOGIN_SUCCESS, Constants.LOGOUT, Constants.REGISTER))
        {
            loginRecord.setStatus(Constants.LOGIN_SUCCESS_STATUS);
        }
        else if (Constants.LOGIN_FAIL.equals(status))
        {
            loginRecord.setStatus(Constants.LOGIN_FAIL_STATUS);
        }

        remoteLogService.saveAccessRecord(loginRecord, SecurityConstants.INNER);
    }
}
