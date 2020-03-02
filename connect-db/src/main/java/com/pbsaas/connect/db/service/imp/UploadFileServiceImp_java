/**
*@Project: subway-backend
*@Author: sam
*@Date: 2016年12月13日
*@Copyright: 2016  All rights reserved.
*/
package com.pbsaas.connect.db.service.imp;

import java.util.List;

import com.pbsaas.connect.db.entity.cms.UploadFile;
import com.pbsaas.connect.db.entity.cms.UploadFileRelate;
import com.pbsaas.connect.db.repository.cms.UploadFileRelateRepository;
import com.pbsaas.connect.db.repository.cms.UploadFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import com.pbsaas.connect.db.service.UploadFileService;


/**
 * @author sam
 *
 */
@Component("uploadFileService")
public class UploadFileServiceImp implements UploadFileService{

	@Autowired
	private UploadFileRepository uploadFileRepository;
	
	@Autowired
	private UploadFileRelateRepository uploadFileRelateRepository;
	
	@Override
	public UploadFile save(UploadFile m) {
		
		return uploadFileRepository.save(m);
	}

	@Override
	public void delete(String id) {
		uploadFileRepository.deleteById(id);
	}

	@Override
	public Page<UploadFile> getByStatus(Integer status, int page, int pageSize) {
		
		return uploadFileRepository.findByStatusOrderByIdDesc(status,  PageRequest.of(page, pageSize));
	}

	@Override
	public UploadFile findById(String id) {

		return uploadFileRepository.findById(id).orElse(null);
	}

	@Override
	public UploadFileRelate saveRelate(UploadFileRelate m) {
		
		return uploadFileRelateRepository.save(m);
	}

	@Override
	public void deleteRelate(Long id) {
		
		uploadFileRelateRepository.deleteById(id);
		
	}
	
	@Override
	public UploadFileRelate findRelateById(Long id) {

		return uploadFileRelateRepository.findById(id).orElse(null);
	}
	
	@Override
	public List<UploadFileRelate> findRelateById(Integer ref_type,Long ref_id) {

		return uploadFileRelateRepository.findByRefTypeAndRefId(ref_type, ref_id);
	}
	
	
}
