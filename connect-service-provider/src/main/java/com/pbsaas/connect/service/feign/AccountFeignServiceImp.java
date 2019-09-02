package com.pbsaas.connect.service.feign;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.pbsaas.connect.model.dto.ModifyAccountDTO;
import com.pbsaas.connect.model.dto.RegistAccountDTO;
import com.pbsaas.connect.model.dto.SearchAccountDTO;
import com.pbsaas.connect.model.vo.AccountVO;
import com.pbsaas.connect.model.vo.PageVO;
import com.pbsaas.connect.model.vo.ResultVO;
import com.pbsaas.connect.service.AccountFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author sam
 *
 */
@RestController
public class AccountFeignServiceImp  implements AccountFeignService {

	@Override
	public ResultVO<String> regist(RegistAccountDTO dto) {
		return null;
	}

	@Override
	public AccountVO findById(String id) {
		return null;
	}

	@Override
	public AccountVO findByName(String name) {
		return null;
	}

	@Override
	public AccountVO findByMobile(String phone) {
		return null;
	}

	@Override
	public void delete(String id) {

	}

	@Override
	public PageVO<AccountVO> search(SearchAccountDTO m, int page, int pageSize) {
		return null;
	}

	@Override
	public void update(ModifyAccountDTO dto) {

	}

	@Override
	public AccountVO find4Third(String from, String openid) {
		return null;
	}
}
