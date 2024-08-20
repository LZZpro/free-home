package com.freehome.domain;

import javax.persistence.Table;

/**
 * @Author：Linzz
 * @Describe:
 * @Date：2024/8/11 19:20
 */
@Table(name = "sys_user")
public class SysUser {

    /**
     * 用户名id
     */
    private Long userId;

    /**
     * 用户名/昵称
     */
    private String userName;

    /**
     * 加密后的密码
     */
    private String encryPassword;

    /** 用户邮箱 */
    private String email;

    /** 手机号码 */
    private String phoneNumber;

    /** 用户头像 */
    private String avatar;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEncryPassword() {
        return encryPassword;
    }

    public void setEncryPassword(String encryPassword) {
        this.encryPassword = encryPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
