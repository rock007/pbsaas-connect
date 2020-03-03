/**
* sam@here 2019年10月28日
**/
package com.pbsaas.connect.framework.dao;

import com.pbsaas.connect.core.model.PageModel;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface SqlDynamicExec {

	 PageModel<Map<String, Object>> queryByDynamicName(String sqlId, Map<String, Object> params, int pageIndex, int pageSize);

	<T extends Serializable> PageModel<T> queryByDynamicName(String sqlId, Map<String, Object> params, Class<T> clazz, int pageNum, int pageSize);

	//list
	 List<Map<String, Object>> queryByDynamicName(String sqlId, Map<String, Object> params);

	<T extends Serializable> List<T> queryByDynamicName(String sqlId, Map<String, Object> params, Class<T> clazz);

	//query int/long
	<T> T queryByRawSql(String rawSql, Class<T> clazz);
	 
	//能用于jpa实体类（更新）
	<T> List<T>  queryListByRawSql(String rawSql, Class<T> clazz);
	 
	void excByRawSql(String rawSql);
}


