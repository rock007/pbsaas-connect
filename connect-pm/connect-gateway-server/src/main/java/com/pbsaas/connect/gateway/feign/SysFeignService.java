/**
* sam@here 2019年10月30日
**/
package com.pbsaas.connect.gateway.feign;

import java.util.Map;

import com.pbsaas.connect.core.model.JsonBody;
import com.pbsaas.connect.core.model.PageModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="system-service")
public interface SysFeignService {

	@PostMapping(value = "/permission-search")
	public JsonBody<PageModel<Map<String, Object>>> searchPermission(@RequestBody Map<String, Object> parameters, @RequestParam("pageIndex")  int pageIndex, @RequestParam("pageSize")  int pageSize);
	
}


