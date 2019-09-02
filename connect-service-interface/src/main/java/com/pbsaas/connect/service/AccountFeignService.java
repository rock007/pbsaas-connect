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

/**
 * @author sam
 *
 */
@FeignClient(value = "accountFeignService")
public interface AccountFeignService {

	public ResultVO<String> regist(RegistAccountDTO dto);
	
	public AccountVO findById(String id);
	
	public AccountVO findByName(String name);
	
	public AccountVO findByMobile(String phone);

	public void delete(String id);
	
	public PageVO<AccountVO> search(SearchAccountDTO m, int page, int pageSize);

	public void update(ModifyAccountDTO dto);

	public AccountVO find4Third(String from,String openid);
}
