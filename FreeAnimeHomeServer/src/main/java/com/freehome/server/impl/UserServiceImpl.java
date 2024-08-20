package com.freehome.server.impl;

import com.freehome.api.domain.SysUserSecret;
import com.freehome.api.model.UserInfo;
import com.freehome.common.core.context.SecurityContextHolder;
import com.freehome.common.core.utils.StringUtils;
import com.freehome.dao.UserInfoRepository;
import com.freehome.domain.SysUser;
import com.freehome.security.utils.IdGeneratorUtil;
import com.freehome.security.utils.SecurityUtils;
import com.freehome.server.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Author：Linzz
 * @Describe:
 * @Date：2024/8/8 14:10
 */
@Service
public class UserServiceImpl implements IUserService {

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserInfoRepository userInfoRepository;
    /**
     * 通过用户名（用户昵称）获取用户信息
     *
     * @param userName
     * @return
     */
    @Override
    public SysUserSecret getUerInfoByUserName(String userName) {
        return userInfoRepository.findSysUserByUsername(userName);
        // return userInfoRepository;
    }

    /**
     * 注册用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public boolean registerUser(SysUserSecret user)
    {
       try {
           user.setUserId(IdGeneratorUtil.getId());
           user.setEncryPassword(SecurityUtils.encryptPassword(user.getEncryPassword()));
           userInfoRepository.save(user);
       }catch (Exception e){
           logger.error("用户注册失败:",e);
           return false;
       }
        return true;
    }

    /**
     * 校验用户名称是否唯一
     *
     * @param user 用户信息
     * @return 结果 true ==唯一
     */
    @Override
    public boolean checkUserNameUnique(SysUserSecret user) {
        String userName = StringUtils.isNull(user.getUserName()) ? ":null" : user.getUserName();
        SysUserSecret info = userInfoRepository.findSysUserByUsername(userName);
        if (info == null || !userName.equals(info.getUserName())) {
            //不相等，唯一
            return true;
        }
        return false;
    }
}
