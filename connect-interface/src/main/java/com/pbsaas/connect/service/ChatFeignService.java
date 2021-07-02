package com.pbsaas.connect.service;

import com.pbsaas.connect.core.model.JsonBody;
import com.pbsaas.connect.core.model.PageModel;
import com.pbsaas.connect.model.dto.SearchMessageDTO;
import com.pbsaas.connect.model.dto.SendMsgDTO;
import com.pbsaas.connect.model.dto.SendMsgGroupDTO;
import com.pbsaas.connect.model.vo.MessageVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *  消息
 * @author
 *
 */

@FeignClient(value = "connect-provider",contextId = "chatFeignService")
@RequestMapping(value="/chat")
public interface ChatFeignService {
	
    public JsonBody<String> send2One(SendMsgDTO dto);

    public JsonBody<String> send2Group(SendMsgGroupDTO dto);

    public JsonBody<PageModel<MessageVO>> search(SearchMessageDTO m, int page, int pageSize);

}
