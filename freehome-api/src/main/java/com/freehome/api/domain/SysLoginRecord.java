package com.freehome.api.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author：Linzz
 * @Describe: 系统访问记录表
 * @Date：2024/8/6 16:30
 */
@Entity
@Table(name = "sys_login_record")
public class SysLoginRecord {

    private static final long serialVersionUID = 1L;

    /** ID */
    @Id
    @GeneratedValue
    @Column(name = "info_id")
    private Long infoId;

    /** 用户账号 */
    @Column(name = "user_name")
    private String userName;

    /** 状态 0成功 1失败 */
    @Column(name = "status")
    private String status;

    /** 地址 */
    @Column(name = "ip_address")
    private String ipaddr;

    /** 描述 */
    @Column(name = "msg")
    private String msg;

    /** 访问时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "access_time")
    private Date accessTime;

    public Long getInfoId() {
        return infoId;
    }

    public void setInfoId(Long infoId) {
        this.infoId = infoId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getAccessTime() {
        return accessTime;
    }

    public void setAccessTime(Date accessTime) {
        this.accessTime = accessTime;
    }
}
