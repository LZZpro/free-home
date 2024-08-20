package com.freehome.api.factory;

import com.freehome.api.RemoteLogService;
import com.freehome.common.core.domain.R;
import com.freehome.api.domain.SysLoginRecord;
import com.freehome.api.domain.SysOperateLogRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @Author：Linzz
 * @Describe: 日志服务降级处理
 * @Date：2024/8/6 18:33
 */
@Component
public class RemoteLogFallbackFactory implements FallbackFactory<RemoteLogService>
{
    private static final Logger logger = LoggerFactory.getLogger(RemoteLogFallbackFactory.class);

    @Override
    public RemoteLogService create(Throwable cause) {
        logger.error("日志服务调用失败:{}", cause.getMessage());
        return new RemoteLogService() {
            @Override
            public R<Boolean> saveLog(SysOperateLogRecord sysOperateLog, String source) throws Exception {
                return R.fail("保存日志操作失败："+ cause.getMessage());
            }

            @Override
            public R<Boolean> saveAccessRecord(SysLoginRecord sysLoginRecord, String source) {
                return R.fail("保存登录日志失败:" + cause.getMessage());
            }
        };
    }
}
