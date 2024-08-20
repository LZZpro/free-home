package com.freehome.dao;

import com.freehome.api.domain.SysLoginRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author：Linzz
 * @Describe: 登陆日志信息
 * @Date：2024/8/15 16:47
 */
@Repository
public interface LoginInfoRepository extends JpaRepository<SysLoginRecord,Long> {
}
