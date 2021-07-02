/**
 *
 */
package com.pbsaas.connect.service;

import java.util.List;
import java.util.Map;

import com.pbsaas.connect.core.model.JsonBody;
import com.pbsaas.connect.core.model.PageModel;
import com.pbsaas.connect.model.dto.ModifyAccountDTO;
import com.pbsaas.connect.model.dto.RegistAccountDTO;
import com.pbsaas.connect.model.dto.SearchAccountDTO;
import com.pbsaas.connect.model.vo.AccountVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author sam
 *
 */
@FeignClient(value = "connect-provider",contextId = "accountFeignService")
@RequestMapping(value="/account")
public interface AccountFeignService {

	@RequestMapping(value = "findById",method = RequestMethod.GET)
	public JsonBody<AccountVO> findById(@RequestParam("id") String id);

	@RequestMapping(value = "findByName",method = RequestMethod.GET)
	public JsonBody<AccountVO> findByName(@RequestParam("name") String name);

	@PostMapping("/regist")
	public JsonBody<String> regist(@RequestBody RegistAccountDTO params);

	@PostMapping("/update")
	public JsonBody<String> update(@RequestBody Map<String,Object> params);

	@PostMapping("/reset-pwd")
	public JsonBody<String> resetPwd(@RequestBody Map<String,Object> params);

	@PostMapping("/search")
	public JsonBody<PageModel<AccountVO>> search(@RequestBody Map<String,Object> params, @RequestParam(value = "pageIndex", required = false, defaultValue = "1") int pageIndex,
												@RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize);
}
