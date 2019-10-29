package com.pbsaas.connect.web.web.backend.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pbsaas.connect.db.type.RoleType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.pbsaas.connect.db.entity.Account;
import com.pbsaas.connect.db.entity.AccountRoles;
import com.pbsaas.connect.db.entity.ActLog;
import com.pbsaas.connect.db.service.AccountService;

/**
 * @author sam
 *
 */

public abstract class BaseController  {

	private static final Logger logger = LoggerFactory.getLogger(BaseController.class);
	
	private static SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddHHmmss");

	@Autowired
	AccountService accountService;
	
	@ExceptionHandler
	public String handleException(Model  model,HttpServletRequest request, HttpServletResponse response,  Exception ex){
	   
		logger.error("handleException", ex);
		String errorMessage = (ex != null ? ex.getMessage() : "Unknown error");
	    
		String url=request.getRequestURI();
		
		if(url.length()>256){
			url=url.substring(url.length()-260);
		}
		
		addLog(url,"error",request.getQueryString(),ex.getLocalizedMessage(),request.getParameter("user_id"),request.getRemoteAddr());
		
		model.addAttribute("status", response.getStatus());
	    model.addAttribute("url", request.getRequestURI()+"		queryStr:"+request.getQueryString());
	    model.addAttribute("errorMessage", errorMessage);
	    model.addAttribute("time", new Date());    
	    
		return "error" ;
	 }

	public static String GetCurDT(){	            
        return sd.format(new java.util.Date());
	}
	
	public  UserDetails getCurUser(){

		UserDetails curUser=null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			curUser = (UserDetails)principal;
		}
		return curUser;
	}
	
	public Account getCurAccount(){
		
		UserDetails cur=getCurUser();
		
		if(cur!=null){
		
			return accountService.findById(cur.getUsername());
		}
		
		return null;
	}
	
	public AccountRoles getCurAccountRoles(){
		
		//single role
		AccountRoles element=null;
		
		UserDetails cur=getCurUser();
		List<RoleType> roles= getCurUserRole();
		
		if(cur!=null){
			
			element=accountService.findRole(cur.getUsername(), roles.get(0));			
		}
		
		return element;
	}
	
	protected void addLog(String title,String msg,String content,String error,String create_user,String  create_ip){
		
		ActLog log=new ActLog(title,msg,content,error,create_user,create_ip);
		//eventBus.notify("quotes", Event.wrap(log));
	}
	

	protected void addLog(String title,String msg,String content,String  create_ip){
		
		String create_user="";
		if(getCurUser()!=null){
			create_user=getCurUser().getUsername();
		}	
			
		ActLog log=new ActLog(title,msg,content,"",create_user,create_ip);
		//eventBus.notify("quotes", Event.wrap(log));
	}
	
	public  List<RoleType> getCurUserRole(){

		List<RoleType> roles=new ArrayList<>();
		UserDetails curUser=getCurUser();
	
		Iterator<? extends GrantedAuthority> iter = curUser.getAuthorities().iterator();
		while (iter.hasNext()){
		    	
		    roles.add(RoleType.valueOf(RoleType.class,iter.next().getAuthority().replaceAll( "ROLE_", "")));
		 }
		
		 return roles;
	}
	
	@ModelAttribute("get_user_name")
    public String get_user_name(String user_id) {
        return "ooooo:"+user_id;
    }
}
