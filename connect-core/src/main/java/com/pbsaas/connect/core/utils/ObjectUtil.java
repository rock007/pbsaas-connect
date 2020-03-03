/**
* sam@here 2019年10月14日
**/
package com.pbsaas.connect.core.utils;

import java.util.Date;
import java.util.Map;

public class ObjectUtil {
	
	public static String parseObj2String(Object obj) {
		
		if(obj==null) return "";
		return obj.toString();
	}
	
	public static Integer parseObj2Integer(Object obj) {
		
		if(obj==null) return 0;
		try {
			
			if(obj.toString().trim().equals("")) return 0;
			
			return Integer.parseInt(obj.toString()) ;			
		}catch(Exception ex) {
		
			ex.printStackTrace();
		}
		return 0;
	}
	
	public static Long parseObj2Long(Object obj) {
		
		if(obj==null) return 0L;
		try {
			
			if(obj.toString().trim().equals("")) return 0L;
			
			return Long.parseLong(obj.toString()) ;			
		}catch(Exception ex) {
		
			ex.printStackTrace();
		}
		return 0L;
	}
	
	public static Date parseObj2Date(Object obj) {
		
		if(obj==null) return null;
		try {
			return (Date)obj ;			
		}catch(Exception ex) {
		
			ex.printStackTrace();
		}
		return null;
	}
	
}


