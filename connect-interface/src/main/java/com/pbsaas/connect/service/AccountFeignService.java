/**
 *
 */
package com.pbsaas.connect.service;

import java.util.List;

import com.pbsaas.connect.model.dto.ModifyAccountDTO;
import com.pbsaas.connect.model.dto.RegistAccountDTO;
import com.pbsaas.connect.model.dto.SearchAccountDTO;
import com.pbsaas.connect.model.vo.AccountVO;
import com.pbsaas.connect.model.vo.PageVO;
import com.pbsaas.connect.model.vo.ResultVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author sam
 *
 */
@FeignClient(value = "connect-provider")
public interface AccountFeignService {

	@RequestMapping(value = "findById")
	public AccountVO findById(@RequestParam("id") String id);

	@RequestMapping(value = "findByName")
	public AccountVO findByName(@RequestParam("name") String name);

}
