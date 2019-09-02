package com.pbsaas.connect.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.pbsaas.connect.db.entity.SysDict;

/**
 * 数据字典
 * 
 * @author 
 *
 */
public interface SysDictRepository extends CrudRepository<SysDict, Long>, JpaSpecificationExecutor<SysDict> {

	List<SysDict> findAll();

	List<SysDict> findByMtype(Long mtype);
}
