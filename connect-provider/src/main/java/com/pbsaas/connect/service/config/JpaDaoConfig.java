package com.pbsaas.connect.service.config;

import com.pbsaas.connect.framework.dao.Dao;
import com.pbsaas.connect.framework.dao.impl.JpaDao;
import com.pbsaas.connect.framework.dao.sql.support.SqlSupportFactory;
import com.pbsaas.connect.framework.dao.sql.template.SqlTemplateManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.persistence.EntityManager;

/**
 * 
 * JPA的Dao实现初始化配置
 */
@Configuration
public class JpaDaoConfig {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private Environment env;

    @Bean(name = "jpaDao")
    public Dao jpaDao() {
        // 读取dao相关配置
        String dbType = env.getProperty("dao.sql.dbType", "Oracle");
        String templateType = env.getProperty("dao.sql.template", "groovy");
        String sqlPath = env.getProperty("dao.sql.path", "classpath:config/sql/*.xml");

        // SQL语句支持工厂设置默认数据库类型
        SqlSupportFactory.setDefaultType(dbType);
        // SQL模板管理器初始化
        SqlTemplateManager.init(sqlPath, templateType);

        JpaDao dao = new JpaDao();
        dao.setEntityManager(entityManager);

        return dao;
    }

}
