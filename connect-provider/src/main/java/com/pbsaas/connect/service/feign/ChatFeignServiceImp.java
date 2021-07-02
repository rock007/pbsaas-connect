package com.pbsaas.connect.service.feign;


import com.pbsaas.connect.core.model.JsonBody;
import com.pbsaas.connect.core.model.PageModel;
import com.pbsaas.connect.framework.controller.JsonBaseController;
import com.pbsaas.connect.model.dto.SearchMessageDTO;
import com.pbsaas.connect.model.dto.SendMsgDTO;
import com.pbsaas.connect.model.dto.SendMsgGroupDTO;
import com.pbsaas.connect.model.vo.MessageVO;
import com.pbsaas.connect.service.ChatFeignService;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatFeignServiceImp extends JsonBaseController implements ChatFeignService {

	@Override
	public JsonBody<String> send2One(SendMsgDTO dto) {
		return null;
	}

	@Override
	public JsonBody<String> send2Group(SendMsgGroupDTO dto) {
		return null;
	}

	@Override
	public JsonBody<PageModel<MessageVO>> search(SearchMessageDTO m, int page, int pageSize) {
		return null;
	}
}
