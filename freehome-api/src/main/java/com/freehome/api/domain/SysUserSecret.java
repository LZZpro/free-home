package com.freehome.api.domain;


import javax.annotation.processing.Generated;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serial;
import java.io.Serializable;

/**
 * @Author：Linzz
 * @Describe: 用户登陆的绝密信息 表名sys_user_secret
 * @Date：2024/8/8 17:53
 */
@Entity  //要理解@Entity 请看FreeHome文档.md {@注解使用明细：}
@Table(name = "sys_user_secret")
public class SysUserSecret implements Serializable {


    @Serial
    private static final long serialVersionUID = 5970933834568463529L;
    /**
     * 用户名id
     */
    @Id
    @Column(name = "user_id")
    private Long userId;

    /**
     * 用户名/昵称
     */
    @Column(name = "user_name",nullable = false,unique = true)
    @NotBlank(message = "用户名不能为空")
    private String userName;

    /**
     * 加密后的密码
     */
    @Column(name = "encry_password",nullable = false)
    @NotBlank(message = "密码不能为空")
    private String encryPassword;

    /** 用户邮箱 */
    @Column(name = "email")
    private String email;

    /** 手机号码 */
    @Column(name = "phone_number")
    private String phoneNumber;

    /** 用户头像 */
    @Column(name = "avatar")
    private String avatar;

    public SysUserSecret(Long userId, String userName, String encryPassword, String email, String phoneNumber, String avatar) {
        this.userId = userId;
        this.userName = userName;
        this.encryPassword = encryPassword;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.avatar = avatar;
    }

    public SysUserSecret(){}

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

    @Override
    public String toString() {
        return "SysUserSecret{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", encryPassword='" + encryPassword + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
