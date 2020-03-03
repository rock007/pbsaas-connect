/**
* sam@here 2019年11月12日
**/
package com.pbsaas.connect.service.event;

import java.util.Date;
import java.util.Map;

import com.pbsaas.connect.db.entity.ActLog;
import com.pbsaas.connect.db.repository.ActLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@Component
public class SysLogReceiver  implements  MessageListener {

	static final Logger logger = LoggerFactory.getLogger(SysLogReceiver.class);
	
	@Autowired
	private ActLogRepository actLogRepository;
	
    @Autowired
    private ObjectMapper mapper;
    
	@Override
	public void onMessage(Message message, byte[] pattern) {
		
		String topic=new String(pattern);
		
		try {
			String body=new String(message.getBody(),"UTF-8");
			
			logger.debug(" receive topic:"+topic+",body:"+body);
			
			Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
			Map<String,Object> args = gson.fromJson(body,new TypeToken<Map<String,Object>>() {}.getType()); 
			
			if(args!=null&&args.size()>0) {


				ActLog log=mapper.convertValue(args, ActLog.class);
				
				log.setCreate_date(new Date());
				actLogRepository.save(log);

			}else {
				logger.warn("传递的参数不对");
			}
			
		}catch(Exception ex) {
			
			logger.error("onMessage",ex);
		}
	}
}


