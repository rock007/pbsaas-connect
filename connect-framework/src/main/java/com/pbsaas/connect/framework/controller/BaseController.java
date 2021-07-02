package com.pbsaas.connect.framework.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author sam
 *
 */
public abstract class BaseController {

	private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

	@ExceptionHandler
	public String handleException(Model  model,HttpServletRequest request, HttpServletResponse response,  Exception ex){
	   
		logger.error("handleException", ex);
		String errorMessage = (ex != null ? ex.getMessage() : "Unknown error");
	    
		String url=request.getRequestURI();
		
		if(url.length()>256){
			url=url.substring(url.length()-260);
		}
		
		//!!!addLog(url,"error",request.getQueryString(),ex.getLocalizedMessage(),request.getParameter("user_id"),request.getRemoteAddr());
		
		model.addAttribute("status",500);
	    model.addAttribute("url", request.getRequestURI()+"		queryStr:"+request.getQueryString());
	    model.addAttribute("errorMessage", errorMessage);
	    model.addAttribute("time", new Date());    
	    
		return "error" ;
	 }

	@ModelAttribute("get_user_name")
    public String get_user_name(String user_id) {
        return "ooooo:"+user_id;
    }
}
