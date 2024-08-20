package com.freehome.api;

import com.freehome.common.core.constants.SecurityConstants;
import com.freehome.common.core.constants.ServiceNameConstants;
import com.freehome.common.core.domain.R;
import com.freehome.api.domain.SysLoginRecord;
import com.freehome.api.domain.SysOperateLogRecord;
import com.freehome.api.factory.RemoteLogFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @Author：Linzz
 * @Describe: 日志服务
 * @Date：2024/8/6 17:51
 */
@FeignClient(contextId = "remoteLogService", value = ServiceNameConstants.SYSTEM_SERVICE, fallbackFactory = RemoteLogFallbackFactory.class)
public interface RemoteLogService {
    /**
     * 保存系统日志
     *
     * @param sysOperateLog 日志实体
     * @param source 请求来源
     * @return 结果
     */
    @PostMapping("/operlog")
    R<Boolean> saveLog(@RequestBody SysOperateLogRecord sysOperateLog, @RequestHeader(SecurityConstants.FROM_SOURCE) String source) throws Exception;

    /**
     * 保存访问记录
     *
     * @param sysLoginRecord 访问实体
     * @param source 请求来源
     * @return 结果
     */
    @PostMapping("/log-record")
    R<Boolean> saveAccessRecord(@RequestBody SysLoginRecord sysLoginRecord, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
