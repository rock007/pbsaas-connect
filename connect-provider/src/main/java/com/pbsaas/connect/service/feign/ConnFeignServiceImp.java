package com.pbsaas.connect.service.feign;

import java.util.Date;
import java.util.List;

import com.pbsaas.connect.model.dto.NewConnectDTO;
import com.pbsaas.connect.model.dto.UpdateConnectDTO;
import com.pbsaas.connect.model.vo.ConnectClientVO;
import com.pbsaas.connect.model.vo.ConnectServerVO;
import com.pbsaas.connect.model.vo.ResultVO;
import com.pbsaas.connect.service.ConnFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.springframework.web.bind.annotation.RestController;


/**
 * @author sam
 *
 */
@RestController
public class ConnFeignServiceImp implements ConnFeignService{

	@Override
	public ResultVO<String> add(NewConnectDTO m) {
		return null;
	}

	@Override
	public ResultVO<String> delete(String cientId) {
		return null;
	}

	@Override
	public ResultVO<String> update(UpdateConnectDTO dto) {
		return null;
	}

	@Override
	public ConnectClientVO findById(String clientId) {
		return null;
	}

	@Override
	public List<ConnectClientVO> findByUserId(String uid) {
		return null;
	}

	@Override
	public ResultVO<String> clear(String serviceId) {
		return null;
	}

	@Override
	public List<ConnectServerVO> findServerList() {
		return null;
	}
}
