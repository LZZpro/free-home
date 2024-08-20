package com.freehome.server;

import com.freehome.api.domain.SysUserSecret;
import com.freehome.api.model.UserInfo;
import com.freehome.domain.SysUser;

/**
 * @Author：Linzz
 * @Describe:
 * @Date：2024/8/8 14:06
 */
public interface IUserService {

    /**
     * 通过用户名（用户昵称）获取用户信息
     * @param userName
     * @return
     */
    SysUserSecret getUerInfoByUserName(String userName);


    /**
     * 注册用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    boolean registerUser(SysUserSecret user);


    /**
     * 校验用户名称是否唯一
     *
     * @param user 用户信息
     * @return 结果
     */
    boolean checkUserNameUnique(SysUserSecret user);

}
