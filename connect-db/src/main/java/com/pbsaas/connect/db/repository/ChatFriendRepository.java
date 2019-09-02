/**
*@Project: paintFriend_backend
*@Author: sam
*@Date: 2017年5月16日
*@Copyright: 2017  All rights reserved.
*/
package com.pbsaas.connect.db.repository;

import com.pbsaas.connect.db.entity.ChatFriend;
import com.pbsaas.connect.db.entity.ChatFriendIds;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * @author sam
 *
 */
public interface ChatFriendRepository  extends CrudRepository<ChatFriend, ChatFriendIds>,JpaSpecificationExecutor<ChatFriend> {

}
