package com.pbsaas.web.webserver;

import com.paintfriend.backend.utils.LogUtils;
import com.paintfriend.chat.proto.LoginReqOuterClass.LoginReq;
import com.paintfriend.chat.proto.PaintFriend;
import com.pbsaas.connect.db.entity.Account;
import com.pbsaas.connect.db.service.AccountService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


@Path("/")
@Component
public class LoginCgi  {

	private static final Logger logger = LoggerFactory.getLogger(LoginCgi.class);

	@Autowired
	private AccountService accountService;
	
	 @Autowired
	 public PasswordEncoder passwordEncoder;

	 
	/***
	 * 系统登录
	 * @param is
	 * @return
	 */
	@Path("/login")
    @POST()
    @Consumes("application/octet-stream")
    @Produces("application/octet-stream")
    public Response login(InputStream is) {

    	int result=-1;
    	String msg="";
    	
        try {
        	
        	final LoginReq request = LoginReq.parseFrom(is);

            logger.info(LogUtils.format("login request from account=%s, password=%s", request.getAccount(), request.getPassword()));

            Account one= accountService.findByName(request.getAccount());
            
            if(one==null){

            	result=-100;
            	msg="登录失败，请检查用户帐号和密码";
            }
            
            if(passwordEncoder.matches(request.getPassword(), one.getPwd())){
            
            	result=1;
            	msg="登录成功";
            }

        } catch (Exception e) {
            logger.info(LogUtils.format("login  %s", e));
            
            result=-100;
        	msg="登录失败，系统出现异常";
        }
        
        final PaintFriend.RespBody response = PaintFriend.RespBody.newBuilder()
        		.setActid(PaintFriend.ActID.ACT_ID_LOGIN_VALUE)
        		.setResult(result)
        		.setMsg(msg)
        		.build();

        final StreamingOutput stream = new StreamingOutput() {
            public void write(OutputStream os) throws IOException {
                response.writeTo(os);
            }
        };
        return Response.ok(stream).build();

    }
}