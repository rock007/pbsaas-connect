/**
*@Project: paintFriend_backend
*@Author: sam
*@Date: 2017年5月16日
*@Copyright: 2017  All rights reserved.
*/
package com.pbsaas.connect.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.pbsaas.connect.db.entity.ChatGroup;

/**
 * @author sam
 *
 */
public interface ChatGroupRepository  extends CrudRepository<ChatGroup, Long>,JpaSpecificationExecutor<ChatGroup>  {

	@Query(value=" select * from chat_group g where id in ( "+
			"	select  DISTINCT m.group_id from chat_group_member m where m.user_id=:userId "+ 
			") ",nativeQuery=true)
	List<ChatGroup> getGroupsByUserId(@Param("userId") String userId);

}
