package com.pbsaas.connect.service;

import com.pbsaas.connect.model.dto.SearchMessageDTO;
import com.pbsaas.connect.model.dto.SendMsgDTO;
import com.pbsaas.connect.model.dto.SendMsgGroupDTO;
import com.pbsaas.connect.model.vo.MessageVO;
import com.pbsaas.connect.model.vo.PageVO;
import com.pbsaas.connect.model.vo.ResultVO;
import org.springframework.cloud.openfeign.FeignClient;

/**
 *  消息
 * @author
 *
 */
@FeignClient(value = "messageFeignService")
public interface MessageFeignService {
	
    public ResultVO<String> send2One(SendMsgDTO dto);

    public ResultVO<String> send2Group(SendMsgGroupDTO dto);

    public PageVO<MessageVO> search(SearchMessageDTO m, int page, int pageSize);

}
