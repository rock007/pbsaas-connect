/**
*@Project: paintFriend_backend
*@Author: sam
*@Date: 2017年5月16日
*@Copyright: 2017  All rights reserved.
*/
package com.pbsaas.connect.db.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.pbsaas.connect.db.entity.ChatMessage;

/**
 * @author sam
 *
 */
public interface ChatMessageRepository extends CrudRepository<ChatMessage,Long>,JpaSpecificationExecutor<ChatMessage>{

}
