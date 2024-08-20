package com.freehome.common.core.constants;

/**
 * @Author：Linzz
 * @Describe: 缓存常量信息
 * @Date：2024/8/8 15:36
 */
public class CacheConstant {

    /**
     * 密码最大错误次数
     */
    public final static int PASSWORD_MAX_RETRY_COUNT = 5;

    /**
     * 密码锁定时间，默认10（分钟）
     */
    public final static long PASSWORD_LOCK_TIME = 10;

    /**
     * 登录账户密码错误次数 redis key
     */
    public static final String PWD_ERR_CNT_KEY = "pwd_err_cnt:";

    /**
     * 登录IP黑名单 cache key
     */
    public static final String LOGIN_BLACK_IP_LIST = "login.blackIPList";
}
