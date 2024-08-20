package com.freehome.auth.form;

/**
 * @Author：Linzz
 * @Describe: 登陆对象
 * @Date：2024/8/2 18:46
 */
public class UserLogBody
{

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
