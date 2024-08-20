package com.freehome.common.core.enums;

/**
 * @Author：Linzz
 * @Describe: 用户状态
 * @Date：2024/8/8 14:46
 */
public enum UserStatusEnum {
    OK("0","正常"),
    DISABLE("1","账号已停用"),
    DELETED("2","账号已被删除");


    private final String code;
    private final String info;

    UserStatusEnum(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }
}
