/**
* sam@here 2019年10月30日
**/
package com.pbsaas.connect.gateway.feign;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="drugman-web")
public interface WebFeignService {

	@PostMapping(value = "/oauth/token")
	public Map<String, Object> getToken(@RequestHeader("Authorization") String header,@RequestParam("username")  String username,@RequestParam("password")  String password);
	
}


