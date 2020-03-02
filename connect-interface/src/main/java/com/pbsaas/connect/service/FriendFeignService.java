package com.pbsaas.connect.service;

import com.pbsaas.connect.model.dto.*;
import com.pbsaas.connect.model.vo.FriendVO;
import com.pbsaas.connect.model.vo.PageVO;
import com.pbsaas.connect.model.vo.ResultVO;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;
import java.util.Map;

/**
 * @author sam
 *
 */
@FeignClient(value = "friendFeignService")
public interface FriendFeignService {

	ResultVO<String> add(AddFriendDTO dto);

	ResultVO<String> delete(DelFriendDTO dto);

	PageVO<FriendVO> search(SearchFriendDTO m, int page, int pageSize);

}
