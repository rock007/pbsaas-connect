package com.pbsaas.connect.service;

import com.pbsaas.connect.core.model.JsonBody;
import com.pbsaas.connect.core.model.PageModel;
import com.pbsaas.connect.model.dto.*;
import com.pbsaas.connect.model.vo.FriendVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author sam
 *
 */

@FeignClient(value = "connect-provider",contextId = "friendFeignService")
@RequestMapping(value="/friend")
public interface FriendFeignService {

	JsonBody<String> add(AddFriendDTO dto);

	JsonBody<String> delete(DelFriendDTO dto);

	JsonBody<PageModel<FriendVO>>  search(SearchFriendDTO m, int page, int pageSize);

}
