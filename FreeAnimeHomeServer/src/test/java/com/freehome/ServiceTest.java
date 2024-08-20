package com.freehome;

import com.freehome.api.domain.SysUserSecret;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;



/**
 * @Author：Linzz
 * @Describe:
 * @Date：2024/8/11 0:08
 */
@SpringBootTest
public class ServiceTest {

    @Test
    public void ymlTest()
    {
        SysUserSecret sysUser = new SysUserSecret();

        sysUser.setAvatar("ff");
        sysUser.setUserName("Aiden");
        sysUser.setPhoneNumber("17854262173");

        //获取加载配置管理类
        Configuration configuration = new Configuration();

        //不给参数就默认加载hibernate.cfg.xml文件，
        configuration.configure();
        //创建Session工厂对象
        SessionFactory factory = configuration.buildSessionFactory();

        //得到Session对象
        Session session = factory.openSession();

        //使用Hibernate操作数据库，都要开启事务,得到事务对象
        Transaction transaction = session.getTransaction();

        //开启事务
        transaction.begin();

        //把对象添加到数据库中
        session.save(sysUser);

        //提交事务
        transaction.commit();

        //关闭Session
        session.close();
    }
}
