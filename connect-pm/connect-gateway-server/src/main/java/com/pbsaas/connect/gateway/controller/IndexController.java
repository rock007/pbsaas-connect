/**
* sam@here 2019年10月30日
**/
package com.pbsaas.connect.gateway.controller;

import java.util.HashMap;
import java.util.Map;

import com.pbsaas.connect.core.AppConstants;
import com.pbsaas.connect.core.model.JsonBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pbsaas.connect.gateway.feign.SysFeignService;

import reactor.core.publisher.Mono;

@RestController
public class IndexController {

	//@Autowired
	//private WebFeignService webFeignService;
	
	@Autowired
	private SysFeignService sysFeignService;

	@Autowired
	public RedisTemplate<String, Object> redisTemplate;

	 @GetMapping("/err")
	 public Mono<JsonBody<Object>> hello() {

	    JsonBody<Object> body=new JsonBody<Object>(-1,"错误测试");

	    return Mono.just(body);
	 }

	 /**
	 @GetMapping("/getToken")
	 public Mono<JsonBody<Object>> getToken(String username,String password) {

	    JsonBody<Object> body=new JsonBody<Object>(-1,"错误测试");

	    String header="Basic dGVzdDE6dGVzdDExMTE=";

		try {

			Map<String,Object> result= webFeignService.getToken(header, username, password);

			body=new JsonBody<Object>(1,"ok",result);

		}catch(Exception ex) {

			body=new JsonBody<Object>(-1,"获取token报错",ex.getMessage());
		}

	    return Mono.just(body);
	 }
	 ***/
	 @GetMapping("/test")
	 public Mono<JsonBody<Object>> test() {

		Map<String,Object> extra=new HashMap<>();
		extra.put("msgId", "1110");
		extra.put("refId", "001");
		extra.put("refType", 1);

		redisTemplate.convertAndSend(AppConstants.SUB_LOG_TOPIC, extra);

		 return Mono.just(new JsonBody<>(1,"success"));
	 }
}


