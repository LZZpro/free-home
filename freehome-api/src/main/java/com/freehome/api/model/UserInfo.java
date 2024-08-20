package com.freehome.api.model;

import com.freehome.api.domain.SysUserSecret;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author：Linzz
 * @Describe: 用户信息
 * @Date：2024/8/2 18:48
 */
public class UserInfo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

    /**
     * 用户唯一标识
     */
    private String token;

    /**
     * 用户名id
     */
    private Long userid;

    /**
     * 用户名称/昵称
     */
    private String nickName;

    /**
     * 登录时间
     */
    private Date loginTime;

    /** 最后登录时间 */
    private Date loginDate;

    /**
     * 过期时间
     */
    private Long expireTime;

  /**
   * 登录IP地址
   */
    private String ipaddr;

  /**用户账号状态**/
  private String status;

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getIpaddr() {
    return ipaddr;
  }

  public void setIpaddr(String ipaddr) {
    this.ipaddr = ipaddr;
  }

  /**用户的密码信息**/
  private SysUserSecret userSecret;

  public String getToken() {
    return token;
  }

  public Long getUserid() {
    return userid;
  }

  public String getNickName() {
    return nickName;
  }

  public Date getLoginTime() {
    return loginTime;
  }

  public Date getLoginDate() {
    return loginDate;
  }

  public Long getExpireTime() {
    return expireTime;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public void setUserid(Long userid) {
    this.userid = userid;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  public void setLoginTime(Date loginTime) {
    this.loginTime = loginTime;
  }

  public void setLoginDate(Date loginDate) {
    this.loginDate = loginDate;
  }

  public void setExpireTime(Long expireTime) {
    this.expireTime = expireTime;
  }

  public SysUserSecret getUserSecret() {
    return userSecret;
  }

  public void setUserSecret(SysUserSecret userSecret) {
    this.userSecret = userSecret;
  }
}
