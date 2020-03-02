package com.pbsaas.connect.web.web.api.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import com.google.protobuf.Any;
import com.google.protobuf.Message;

import com.pbsaas.connect.web.web.model.JsonBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author sam
 *
 */
 public abstract class JsonBaseController  {

	protected static final Logger logger = LoggerFactory.getLogger(JsonBaseController.class);
	
	protected static SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddHHmmss");
	
	protected static SimpleDateFormat sd_yyyyMM = new SimpleDateFormat("yyyyMM");

    //@Autowired
	//AccountService accountService;

	@ExceptionHandler
	public @ResponseBody  JsonBody<String> handleException(HttpServletRequest request, Exception ex){
	   
		logger.error("handleException", ex);
		
		if(ex instanceof ConstraintViolationException){
			
			Set<ConstraintViolation<?>> violations= ((ConstraintViolationException) ex).getConstraintViolations();
			
			String err="";
			for(ConstraintViolation<?> violation:violations){
				
				String message = violation.getMessage();
				
				err=err+violation.getPropertyPath()+message+",\n";
			}
			
			return new JsonBody<String>(-1,"操作失败，参数输入验证失败",err);
		}

		String url=request.getRequestURI();
		
		if(url.length()>256){
			url=url.substring(url.length()-260);
		}
		
		//!!addLog(url,"error",request.getQueryString(),ex.getLocalizedMessage(),request.getParameter("user_id"),request.getRemoteAddr());
		return new JsonBody<String>(-1,"操作失败，系统出现异常",ex.getMessage()); 
	 }
/**
	protected void addLog(String title,String msg,String content,String error,String create_user,String  create_ip){
		
		ActLog log=new ActLog(title,msg,content,error,create_user,create_ip);
		//eventBus.notify("quotes", Event.wrap(log));
	}
***/

	public static String getCurDT(){	            
        return sd.format(new java.util.Date());
	}
	
	public static String getDTyyyyMM(Date d){	            
        return sd_yyyyMM.format(d);
	}
	
/**
	public  Account getAccInfo(){

        Account curUser=null;
	    UserDetails ud=getCurUser();
	    if(ud!=null){

            curUser= accountService.findByName(ud.getUsername());
        }

		return curUser;
	}
***/
	public  UserDetails getCurUser(){

		if(!SecurityContextHolder.getContext().getAuthentication().isAuthenticated()){

		    return null;
        }

        UserDetails curUser=null;

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			curUser = (UserDetails)principal;
		}
		return curUser;
	}

    protected boolean pushNotify(String text,String uid){

	    return pushNotify(text,uid,null);
    }
	protected boolean pushNotify(String text,String uid,Message params){

		//PaintFriend.ReqBody req;

        try {
            /**
            if(params!=null){

                req = PaintFriend.ReqBody.newBuilder()
                        .setActid(PaintFriend.ActID.ACT_ID_NOTICE_RSP_VALUE)
                        .setText(text)
                        .setToken("")
                        .setUid(uid)
                        .setData(Any.pack(params))
                        .build();

            }else {
                req = PaintFriend.ReqBody.newBuilder()
                        .setActid(PaintFriend.ActID.ACT_ID_NOTICE_RSP_VALUE)
                        .setText(text)
                        .setToken("")
                        .setUid(uid)
                        .build();
            }
            ***/

            /**!!
            if(!SendMsgQueue.getQueue().offer(req)){

                return false;
            }
            ***/
        }catch (Exception ex){

            logger.error("发送通知出错：",ex);
            return  false;
        }

        return true;

    }
}
