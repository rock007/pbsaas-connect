/**
*@Project: paintFriend_backend
*@Author: sam
*@Date: 2017年5月17日
*@Copyright: 2017  All rights reserved.
*/
package com.pbsaas.connect.db.service;

import com.pbsaas.connect.db.entity.ChatMessage;
import org.springframework.data.domain.Page;

/**
 * @author sam
 *
 */
public interface ChatService {

	ChatMessage saveMessage(ChatMessage m);
	
	void deleteMessage(Long id);

    public Page<ChatMessage> search(final ChatMessage m, int page, int pageSize);

}
