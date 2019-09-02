package com.pbsaas.connect.db.service.imp;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.pbsaas.connect.db.repository.SysDictRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.pbsaas.connect.db.entity.SysDict;
import com.pbsaas.connect.db.service.SysDictService;

/**
 * 数据字典
 * @author
 *
 */
@Component("sysDictService")
public class SysDictServiceImpl implements SysDictService {
	@Autowired
	private SysDictRepository sysDictRepository;

	@Override
	public void delete(Long id) {
		sysDictRepository.deleteById(id);
	}

	@Override
	public SysDict save(SysDict s) {
		return sysDictRepository.save(s);
	}

	@Override
	public List<SysDict> findAll() {

		return sysDictRepository.findAll();
	}

	@Override
	public SysDict findById(Long id) {
		return sysDictRepository.findById(id).orElse(null);
	}
	
	@Override
	public  List<SysDict> findByMtype(Long mtype){
		
		return sysDictRepository.findByMtype(mtype);
	}
	
	@Override
	public Page<SysDict> search(final SysDict m, int page, int pageSize) {
		return sysDictRepository.findAll(new Specification<SysDict>() {

			public Predicate toPredicate(Root<SysDict> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

					Predicate p1, p2, pc;

				if (m.getMtype() != null && m.getMtype() > 0) {

					p1 = cb.equal(root.get("mtype").as(Integer.class), m.getMtype());
					pc = cb.and(p1);
				} else {
					p1 = cb.notEqual(root.get("mtype").as(Integer.class), "0");
					pc = cb.and(p1);
				}
				query.where(pc);
				// 添加排序的功能
				query.orderBy(cb.asc(root.get("id").as(Integer.class)));
				return null;
			}

		},  PageRequest.of(page, pageSize));
	}

}
