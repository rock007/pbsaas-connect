/**
*@Project: subway-backend
*@Author: sam
*@Date: 2017年4月21日
*@Copyright: 2017  All rights reserved.
*/
package com.pbsaas.connect.db.service.imp;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pbsaas.connect.db.entity.ConnSession;
import com.pbsaas.connect.db.repository.ConnSessionRepository;
import com.pbsaas.connect.db.service.ConnService;


/**
 * @author sam
 *
 */
@Component("connService")
public class ConnServiceImp implements ConnService{

	@Autowired
	private ConnSessionRepository connSessionRepository;

	@Override
	public ConnSession save(ConnSession m) {
		
		return connSessionRepository.save(m);
		
	}

	@Override
	public void delete(String id) {

		connSessionRepository.deleteById(id);
	}

	@Override
	public ConnSession findById(String id) {
	
		return connSessionRepository.findById(id).orElse(null);
	}

	@Override
	public List<ConnSession> findByUserId(String user_id) {
		
		return connSessionRepository.findByUserId(user_id);
	}
	
	@Override
	public ConnSession updateConn(String user_id,String uid,String ip,String ticket){
		
		ConnSession session= connSessionRepository.findByUserIdAndUid(user_id,uid);
		if(session==null){
			
			session= new ConnSession();
			
			session.setUid(uid);
			session.setUserId(user_id);
		}
		
		session.setIp(ip);
		session.setCreate_date(new Date());
		session.setTicket(ticket);
		return connSessionRepository.save(session);
	}
	@Override
	public void deleteAll(){
		
		connSessionRepository.deleteAll();
	}
	
}
