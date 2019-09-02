package com.pbsaas.connect.db.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pbsaas.connect.db.entity.ActLog;
import com.pbsaas.connect.db.repository.ActLogRepository;
import com.pbsaas.connect.db.service.ActLogService;


@Component("actLogService")
public class ActlogServiceImp implements ActLogService{

	@Autowired
	private ActLogRepository actLogRepository;
	
	@Override
	public void saveLog(ActLog log) {
		
		actLogRepository.save(log);
	}

	@Override
	public List<ActLog> getActLogByOrder(long orderId) {
			
		return null;
		//return actLogRepository.findAll();
	}

	
}
