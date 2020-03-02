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
	public AccountVO findById(String id) {

		AccountVO vo=new AccountVO();
		vo.setUserName("admin");
		vo.setUid(id);
		return vo;
	}

	@Override
	public AccountVO findByName(String name) {

		AccountVO vo=new AccountVO();
		vo.setUserName(name);
		return vo;
	}

}
