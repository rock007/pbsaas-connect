
package com.pbsaas.connect.service.feign;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.pbsaas.connect.model.dto.AddFriendDTO;
import com.pbsaas.connect.model.dto.DelFriendDTO;
import com.pbsaas.connect.model.dto.SearchFriendDTO;
import com.pbsaas.connect.model.vo.FriendVO;
import com.pbsaas.connect.model.vo.PageVO;
import com.pbsaas.connect.model.vo.ResultVO;
import com.pbsaas.connect.service.FriendFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class FriendFeignServiceImp implements FriendFeignService{

	@Override
	public ResultVO<String> add(AddFriendDTO dto) {
		return null;
	}

	@Override
	public ResultVO<String> delete(DelFriendDTO dto) {
		return null;
	}

	@Override
	public PageVO<FriendVO> search(SearchFriendDTO m, int page, int pageSize) {
		return null;
	}
}
