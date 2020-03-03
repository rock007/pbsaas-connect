/**
* sam@here 2019年10月15日
**/
package com.pbsaas.connect.core.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class MapUtils {


    public static Map <String, Object> objectToMap(Object obj){
    	
    	Map <String, Object> map = new HashMap <String, Object>();
        
    	if(obj == null)
    		
            return map;

        try {
        	BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for(PropertyDescriptor property : propertyDescriptors){
                String key = property.getName();
                if(key.compareToIgnoreCase("class") == 0){
                    continue;
                }
                Method getter = property.getReadMethod();
                Object value = getter != null ?getter.invoke(obj) : null;
                map.put(key, value);
            }
        }catch(Exception ex) {
        	
        }
        return map;
    }

    /**
     * 获取val
     */
    public static String getMapVal(Map <String, Object> paramMap, String key){
        if(paramMap == null || key == null){
            return "";
        }
        return paramMap.get(key) == null ?"" : paramMap.get(key).toString();
    }

    public static String getMapValByString(Map <String, String> paramMap, String key){
        if(paramMap == null || key == null){
            return "";
        }
        return paramMap.get(key) == null ?"" : paramMap.get(key).toString();
    }

    /**
     * 实体类转map
     */
    public static Map<String, Object> ConvertObjToMap(Object obj, String... ignoreProperties){
       
		List<String> ignoreList = (ignoreProperties != null ? Arrays.asList(ignoreProperties) : null);

    	Map <String, Object> reMap = new HashMap <String, Object>();
        if(obj == null)
            return null;

        try{
        	BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for(PropertyDescriptor property : propertyDescriptors){
                String key = property.getName();
                if(key.compareToIgnoreCase("class") == 0){
                    continue;
                }
                //加过滤属性
                if(ignoreList != null && ignoreList.contains(key)){
                	
                	continue;
                }
                Method getter = property.getReadMethod();
                Object value = getter != null ?getter.invoke(obj) : null;
                reMap.put(key, value);
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return reMap;
    }

}


