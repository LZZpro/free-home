package com.freehome.dao;

import com.freehome.api.domain.SysUserSecret;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @Author：Linzz
 * @Describe:
 * @Date：2024/8/11 17:33
 */
@Repository
public interface UserInfoRepository extends JpaRepository<SysUserSecret,Long>{

//    @PersistenceContext
//    EntityManager em = null;

    @Query(value = "select new com.freehome.api.domain.SysUserSecret(s.userId,s.userName,s.encryPassword,s.email,s.phoneNumber," +
            "s.avatar) from SysUserSecret s where s.userName = :username")
    SysUserSecret findSysUserByUsername(@Param("username") String username);


//    @Query(value = "inert into sys_user_secret (user_id,user_name,encry_password,email,phone_number,avatar)")
//    int insertSysUserSecret();
}
