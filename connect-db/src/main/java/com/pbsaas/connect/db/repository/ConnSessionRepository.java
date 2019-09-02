/**
*@Project: subway-backend
*@Author: sam
*@Date: 2017年4月21日
*@Copyright: 2017  All rights reserved.
*/
package com.pbsaas.connect.db.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.pbsaas.connect.db.entity.ConnSession;


/**
 * @author sam
 *
 */
public interface ConnSessionRepository extends CrudRepository<ConnSession, String>{

	ConnSession findByUserIdAndUid(String user_id,String uid);
	
	List<ConnSession> findByUserId(String user_id);
	
}
