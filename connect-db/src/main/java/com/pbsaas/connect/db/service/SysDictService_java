package com.pbsaas.connect.db.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.pbsaas.connect.db.entity.SysDict;

public interface SysDictService {
	
	public void delete(Long id);

	public SysDict save(SysDict s);

	public List<SysDict> findAll();

	public SysDict findById(Long id);
	
	public  List<SysDict> findByMtype(Long mtype);

	public Page<SysDict> search(SysDict s, int page, int pageSize);
}
