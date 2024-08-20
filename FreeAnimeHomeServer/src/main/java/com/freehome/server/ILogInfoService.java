package com.freehome.server;

import com.freehome.api.domain.SysLoginRecord;

/**
 * @Author：Linzz
 * @Describe: 访问日志情况信息
 * @Date：2024/8/15 16:42
 */
public interface ILogInfoService {

   boolean insertLoginInfo(SysLoginRecord record);
}
