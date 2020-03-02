package com.pbsaas.connect.service.feign;


import java.util.Date;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.pbsaas.connect.model.dto.SearchMessageDTO;
import com.pbsaas.connect.model.dto.SendMsgDTO;
import com.pbsaas.connect.model.dto.SendMsgGroupDTO;
import com.pbsaas.connect.model.vo.MessageVO;
import com.pbsaas.connect.model.vo.PageVO;
import com.pbsaas.connect.model.vo.ResultVO;
import com.pbsaas.connect.service.MessageFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageFeignServiceImp implements MessageFeignService {

	@Override
	public ResultVO<String> send2One(SendMsgDTO dto) {
		return null;
	}

	@Override
	public ResultVO<String> send2Group(SendMsgGroupDTO dto) {
		return null;
	}

	@Override
	public PageVO<MessageVO> search(SearchMessageDTO m, int page, int pageSize) {
		return null;
	}
}
