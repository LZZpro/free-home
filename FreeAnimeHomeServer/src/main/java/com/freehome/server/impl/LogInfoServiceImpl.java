package com.freehome.server.impl;

import com.freehome.api.domain.SysLoginRecord;
import com.freehome.dao.LoginInfoRepository;
import com.freehome.server.ILogInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author：Linzz
 * @Describe:
 * @Date：2024/8/15 16:45
 */
@Service
public class LogInfoServiceImpl implements ILogInfoService {

    Logger logger = LoggerFactory.getLogger(LogInfoServiceImpl.class);

    @Autowired
    private LoginInfoRepository loginInfo;

    @Override
    public boolean insertLoginInfo(SysLoginRecord record) {
        loginInfo.save(record);
        return true;
    }
}
