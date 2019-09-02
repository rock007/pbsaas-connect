package com.pbsaas.connect.db.repository.cms;

import java.util.List;

import com.pbsaas.connect.db.entity.cms.UploadFileRelate;
import org.springframework.data.repository.CrudRepository;


public interface UploadFileRelateRepository extends CrudRepository<UploadFileRelate, Long> {
	
	List<UploadFileRelate> findByRefTypeAndRefId(Integer ref_type,Long ref_id);
}