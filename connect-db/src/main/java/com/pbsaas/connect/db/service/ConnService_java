/**
*@Project: subway-backend
*@Author: sam
*@Date: 2017年4月21日
*@Copyright: 2017  All rights reserved.
*/
package com.pbsaas.connect.db.service;

import java.util.List;

import com.pbsaas.connect.db.entity.ConnSession;


/**
 * @author sam
 *
 */
public interface ConnService {

	public ConnSession save(ConnSession m);
	
	public void delete(String id);
	
	public ConnSession findById(String id);
	
	List<ConnSession> findByUserId(String user_id);
	
	ConnSession updateConn(String user_id,String uid,String ip,String ticket);
	
	public void deleteAll();
}
