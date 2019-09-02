package com.pbsaas.connect.db.repository;

import com.pbsaas.connect.db.entity.ActLog;
import org.springframework.data.repository.CrudRepository;


public interface ActLogRepository extends CrudRepository<ActLog, Long> {
	
}