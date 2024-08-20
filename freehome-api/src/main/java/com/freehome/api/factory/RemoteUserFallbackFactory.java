package com.freehome.api.factory;

import com.freehome.api.RemoteUserService;
import com.freehome.common.core.domain.R;
import com.freehome.api.domain.SysUserSecret;
import com.freehome.api.model.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @Author：Linzz
 * @Describe: 用户服务降级处理
 * @Date：2024/8/7 13:27
 */
@Component
public class RemoteUserFallbackFactory implements FallbackFactory<RemoteUserService>
{
    private static final Logger logger = LoggerFactory.getLogger(RemoteUserFallbackFactory.class);

    @Override
    public RemoteUserService create(Throwable cause)
    {
        logger.error("用户服务调用失败:{}", cause.getMessage());
        return new RemoteUserService() {

            @Override
            public R<UserInfo> getUserInfo(String username, String source) {
                return R.fail("获取用户失败:" + cause.getMessage());
            }

            /**
             * 注册用户信息
             *
             * @param userSecret 用户信息
             * @param source     请求来源
             * @return 结果
             */
            @Override
            public R<Boolean> registerUserInfo(SysUserSecret userSecret, String source) {
                return R.fail("注册用户失败:" + cause.getMessage());
            }
        };
    }
}
