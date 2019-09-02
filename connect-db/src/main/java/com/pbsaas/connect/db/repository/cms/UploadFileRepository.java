package com.pbsaas.connect.db.repository.cms;

import com.pbsaas.connect.db.entity.cms.UploadFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;


public interface UploadFileRepository extends CrudRepository<UploadFile, String> {
	
	Page<UploadFile> findByStatusOrderByIdDesc(Integer status,Pageable pageable);
}