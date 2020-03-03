/**
* sam@here 2019年10月28日
**/
package com.pbsaas.connect.framework.dao.impl;

import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import com.pbsaas.connect.core.model.PageModel;
import com.pbsaas.connect.framework.dao.SqlDynamicExec;
import com.pbsaas.connect.framework.dao.sql.support.SqlSupport;
import com.pbsaas.connect.framework.dao.sql.support.SqlSupportFactory;
import com.pbsaas.connect.framework.dao.sql.template.SqlResult;
import com.pbsaas.connect.framework.dao.sql.template.SqlTemplateManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import com.google.gson.Gson;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

@Component("sqlDynamicExec")
public class SqlDynamicExecImpl implements SqlDynamicExec {

	private static final Logger logger = LoggerFactory.getLogger(SqlDynamicExec.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@PostConstruct
	public void init() {
		try {

		} catch (Exception e) {
			throw new BeanInitializationException("Initialization of BaseDAO failed", e);
		}
	}
	  
	@Override
	public PageModel<Map<String, Object>> queryByDynamicName(String sqlId, Map<String, Object> params, int pageIndex, int pageSize) {
		
		if( sqlId==null ) {
            logger.error("sqlId not be null");
            return null;
        }

		SqlResult rs = SqlTemplateManager.getSqlResult(sqlId, params);
		SqlSupport sqlSupport = SqlSupportFactory.getSupport();
		String sql=rs.getSql();

		String listSql = sqlSupport.getPageSql(sql, pageIndex, pageSize);
		String countSql = sqlSupport.getTotalSql(sql);

		List<Map<String, Object>> list = new ArrayList<>();

        list= jdbcTemplate.queryForList(listSql);
        
        // 查询总记录数
        int totalCount = jdbcTemplate.queryForObject(countSql, Integer.class) ;

        if(list == null){
            list = Collections.emptyList();
        }
		PageModel<Map<String, Object>> pageBean = new PageModel<Map<String, Object>>(pageIndex,pageSize,totalCount);
        pageBean.setData(list);
        return pageBean;
	    
	}
		
    @Override
	public <T extends Serializable> PageModel<T> queryByDynamicName(String sqlId, Map<String, Object> params,
			Class<T> clazz, int pageIndex, int pageSize) {

		List<T> list = new ArrayList<>();

		SqlResult rs = SqlTemplateManager.getSqlResult(sqlId, params);
		SqlSupport sqlSupport = SqlSupportFactory.getSupport();
		String sql=rs.getSql();

		String listSql = sqlSupport.getPageSql(sql, pageIndex, pageSize);
		String countSql = sqlSupport.getTotalSql(sql);

        List<Map<String,Object>> data= jdbcTemplate.queryForList(listSql);
        
        Gson gson = new Gson();
        
        list=data.stream().map(x->{
        	return gson.fromJson(gson.toJsonTree(x), clazz);
        }).collect(Collectors.toList());
        // 查询总记录数
        int totalCount = jdbcTemplate.queryForObject(countSql, Integer.class) ;
        // 总页数

        if(list == null){
            list = Collections.emptyList();
        }
		PageModel<T> pageBean = new PageModel<>(pageIndex,pageSize,totalCount);
        pageBean.setData(list);
        return pageBean;
	}
    
	@Override
	public List<Map<String, Object>> queryByDynamicName(String sqlId, Map<String, Object> params) {

		List<Map<String, Object>>  list = new ArrayList<>();

		SqlResult rs = SqlTemplateManager.getSqlResult(sqlId, params);
		SqlSupport sqlSupport = SqlSupportFactory.getSupport();
		String sql=rs.getSql();

        list= jdbcTemplate.queryForList(sql);
        
        if(list == null){
            list = Collections.emptyList();
        }

        return list;
	}

	@Override
	public <T extends Serializable> List<T> queryByDynamicName(String sqlId, Map<String, Object> params,
			Class<T> clazz) {

		List<T>  list = new ArrayList<>();

		SqlResult rs = SqlTemplateManager.getSqlResult(sqlId, params);
		SqlSupport sqlSupport = SqlSupportFactory.getSupport();
		String sql=rs.getSql();

        List<Map<String,Object>> data= jdbcTemplate.queryForList(sql);
        
        Gson gson = new Gson();
        
        list=data.stream().map(x->{
        	return gson.fromJson(gson.toJsonTree(x), clazz);
        }).collect(Collectors.toList());
        
        if(list == null){
            list = Collections.emptyList();
        }

        return list;
	}
	
	@Override
	public <T> T queryByRawSql(String rawSql, Class<T> clazz) {
		
		try {
			
			return jdbcTemplate.queryForObject(rawSql,  clazz);  
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	@Override
	public <T> List<T> queryListByRawSql(String rawSql, Class<T> clazz) {
		
        List<T> list = new ArrayList<>();
        
		if( rawSql==null||rawSql.equals("") ) {
            logger.error("rawSql not be null");
            return null;
        }
		try {

			List<Map<String, Object>> data = jdbcTemplate.queryForList(rawSql);

			Gson gson = new Gson();

			list = data.stream().map(x -> {
				return gson.fromJson(gson.toJsonTree(x), clazz);
			}).collect(Collectors.toList());

			if (list == null) {
				list = Collections.emptyList();
			}

		} catch (Exception ex) {
			logger.error("queryListByRawSql", ex);
		}
		return list;
	}

	@Override
	public void excByRawSql(String rawSql) {
		
		 jdbcTemplate.execute(rawSql);
	}
}


