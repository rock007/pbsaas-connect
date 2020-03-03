package com.pbsaas.connect.framework.dao.sql.support.impl;

import com.pbsaas.connect.framework.dao.sql.support.SqlSupport;

/**
 * 
 */
public class OracleSqlSupport implements SqlSupport {

    @Override
    public String getPageSql(String sql, int pageNum, int pageSize) {
        int minNo = (pageNum - 1) * pageSize;
        int maxNo = minNo + pageSize;
        sql = "select * from (" +
                "select rownum rownumber, a.* from (" + sql + ") a) b " +
                "where b.rownumber > " + minNo + " and b.rownumber <= " + maxNo;

        return sql;
    }

    @Override
    public String getTotalSql(String sql) {
        return "select count(1) from (" + sql + ") a";
    }

}
