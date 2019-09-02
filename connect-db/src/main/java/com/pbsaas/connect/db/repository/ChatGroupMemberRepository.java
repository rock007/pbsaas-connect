/**
*@Project: paintFriend_backend
*@Author: sam
*@Date: 2017年5月16日
*@Copyright: 2017  All rights reserved.
*/
package com.pbsaas.connect.db.repository;

import com.pbsaas.connect.db.entity.ChatGroupMember;
import com.pbsaas.connect.db.entity.ChatGroupMemberIds;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * @author sam
 *
 */
public interface ChatGroupMemberRepository extends CrudRepository<ChatGroupMember, ChatGroupMemberIds>,JpaSpecificationExecutor<ChatGroupMember> {

}
