package com.pbsaas.connect.framework.controller;

import com.pbsaas.connect.core.model.JsonBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

/**
 * @author sam
 *
 */
 public abstract class JsonBaseController {

	protected static final Logger logger = LoggerFactory.getLogger(JsonBaseController.class);

	@ExceptionHandler
	public @ResponseBody JsonBody<String> handleException(HttpServletRequest request, Exception ex){
	   
		logger.error("handleException", ex);

		String url=request.getRequestURI();
		
		if(url.length()>256){
			url=url.substring(url.length()-260);
		}
		
		//!!addLog(url,"error",request.getQueryString(),ex.getLocalizedMessage(),request.getParameter("user_id"),request.getRemoteAddr());
		return new JsonBody<String>(-1,"操作失败，系统出现异常",ex.getMessage());
	 }

}
