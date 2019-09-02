package com.pbsaas.connect.service;

import com.pbsaas.connect.model.dto.NewConnectDTO;
import com.pbsaas.connect.model.dto.UpdateConnectDTO;
import com.pbsaas.connect.model.vo.ConnectClientVO;
import com.pbsaas.connect.model.vo.ConnectServerVO;
import com.pbsaas.connect.model.vo.ResultVO;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;


/**
 * @author sam
 *
 */
@FeignClient(value = "connFeignService")
public interface ConnFeignService {

	ResultVO<String>  add(NewConnectDTO m);

	ResultVO<String> delete(String cientId);

	ResultVO<String> update(UpdateConnectDTO dto);

	public ConnectClientVO findById(String clientId);
	
	List<ConnectClientVO> findByUserId(String uid);

	ResultVO<String> clear(String serviceId);

	List<ConnectServerVO> findServerList();
}
