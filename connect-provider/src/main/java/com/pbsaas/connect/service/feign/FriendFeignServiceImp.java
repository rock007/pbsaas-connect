
package com.pbsaas.connect.service.feign;

import com.pbsaas.connect.core.model.JsonBody;
import com.pbsaas.connect.core.model.PageModel;
import com.pbsaas.connect.model.dto.AddFriendDTO;
import com.pbsaas.connect.model.dto.DelFriendDTO;
import com.pbsaas.connect.model.dto.SearchFriendDTO;
import com.pbsaas.connect.model.vo.FriendVO;
import com.pbsaas.connect.service.FriendFeignService;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class FriendFeignServiceImp implements FriendFeignService{

	@Override
	public JsonBody<String> add(AddFriendDTO dto) {
		return null;
	}

	@Override
	public JsonBody<String> delete(DelFriendDTO dto) {
		return null;
	}

	@Override
	public JsonBody<PageModel<FriendVO>> search(SearchFriendDTO m, int page, int pageSize) {
		return null;
	}
}
