package com.pbsaas.connect.framework.dao.sql.support;

import com.pbsaas.connect.framework.dao.sql.support.impl.MySQLSqlSupport;
import com.pbsaas.connect.framework.dao.sql.support.impl.OracleSqlSupport;
import com.pbsaas.connect.framework.dao.sql.support.impl.PostgreSQLSqlSupport;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cl on 2017/4/12.
 * SQL语句支持工厂
 */
public class SqlSupportFactory {

    private final static String TYPE_ORACLE = "Oracle";
    private final static String TYPE_MYSQL = "MySQL";
    private final static String TYPE_POSTGRESQL = "PostgreSQL";
    private final static String TYPE_H2 = "H2";

    /**
     * 当前的默认数据库类型
     */
    private static String defaultType;

    private static Map<String, SqlSupport> supports;

    private SqlSupportFactory() {
    }

    static {
        supports = new HashMap<String, SqlSupport>();
        supports.put(TYPE_ORACLE, new OracleSqlSupport());
        supports.put(TYPE_MYSQL, new MySQLSqlSupport());
        supports.put(TYPE_POSTGRESQL, new PostgreSQLSqlSupport());
        supports.put(TYPE_H2, new MySQLSqlSupport());
    }

    /**
     * 获取数据库SQL支持
     */
    public static SqlSupport getSupport(String type) {
        SqlSupport support = supports.get(type);
        if (null == support) {
            support = getSupport();
        }
        return support;
    }

    /**
     * 获取默认的SQL支持
     */
    public static SqlSupport getSupport() {
        // 未配置默认数据库类型时，默认Oracle
        String type = null != defaultType ? defaultType : TYPE_ORACLE;
        return supports.get(type);
    }

    /**
     * 设置当前默认的数据库类型
     */
    public static void setDefaultType(String type) {
        defaultType = type;
    }

}
