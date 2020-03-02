/**
*@Project: subway-backend
*@Author: sam
*@Date: 2016年12月12日
*@Copyright: 2016  All rights reserved.
*/
package com.pbsaas.connect.db.service.imp;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.pbsaas.connect.db.type.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.pbsaas.connect.db.entity.Account;
import com.pbsaas.connect.db.entity.AccountRoles;
import com.pbsaas.connect.db.entity.AccountRolesIds;
import com.pbsaas.connect.db.repository.AccountRepository;
import com.pbsaas.connect.db.repository.AccountRolesRepository;
import com.pbsaas.connect.db.service.AccountService;


/**
 * @author sam
 *
 */
@Component("accountService")
public class AccountServiceImp  implements AccountService {

	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private AccountRolesRepository accountRolesRepository;

	@Override
	@Cacheable("acount_all")
	public List<Account> findAll(){
		
		return accountRepository.findAll();
	}
	
	@Override
	public Account findById(String id){
		
		return accountRepository.findById(id).orElse(null);
	}
	
	@Override
	public Account findByName(String name) {
	
		return accountRepository.findByName(name);
	}

	@Override
	public Account findByMobile(String mobile) {
		
		return accountRepository.findByMobile(mobile);
	}

	@Override
	public Account save(Account m) {
		
		return accountRepository.save(m);
	}

	@Override
	public void delete(String id){
		
		accountRepository.deleteById(id);
		
	}
	
	@Override
	public Page<Account> search(final Account m,int page,int pageSize){
		
		return accountRepository.findAll(new Specification<Account>() {

			public Predicate toPredicate(Root<Account> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {

				String name = m.getName();//用户姓名

				String phoneno = m.getMobile();//手机号
				//Integer status = m.getStatus(); //状态
				
				Predicate p1,p2,p3,p4,pc;

				if (!StringUtils.isEmpty(name)) {
					p1 = cb.like(root.get("name").as(String.class), "%"
							+ name.toLowerCase() + "%");
					
					pc=cb.and(p1);

				} else {
					p1 = cb.notEqual(root.get("id").as(String.class), "0");
					pc=cb.and(p1);
				
				}
				
				if(!StringUtils.isEmpty(phoneno)){
					
					p2 =cb.equal(root.get("mobile").as(String.class),phoneno);
					pc=cb.and(pc,p2);
				}
				/*
				if(status != null){
	
					p4 =cb.equal(root.get("status").as(Integer.class),status);
					pc=cb.and(pc,p4);
				}
				***/
				query.where(pc);

				// 添加排序的功能
				//query.orderBy(cb.desc(root.get("id").as(Long.class)));

				return null;
			}

		},  PageRequest.of(page, pageSize));
	}
	
	@Override
	public AccountRoles findRole(String user_id, RoleType role_id){
		
		AccountRolesIds id=new AccountRolesIds();
		id.setUser_id(user_id);
		id.setRole_id(role_id);
		return accountRolesRepository.findById(id).orElse(null);
	}

	@Override
	public void addRole(String user_id,RoleType role_id ){
	
		AccountRolesIds id=new AccountRolesIds();
		id.setUser_id(user_id);
		id.setRole_id(role_id);
		
		AccountRoles entity=new AccountRoles();
		entity.setAccountRolesIds(id);
		accountRolesRepository.save(entity);
	}
	
	@Override
	public void delRoles(String user_id,RoleType role_id){
		
		AccountRolesIds id=new AccountRolesIds();
		id.setUser_id(user_id);
		id.setRole_id(role_id);
		
		if( findRole(user_id,role_id)!=null)
			accountRolesRepository.deleteById(id);;
	}
	
	public Account find4Third(String from,String openid){
		
		Account oneUser=null;
    	if(from.equals("qq")){
    		
    		oneUser=accountRepository.findByQqOpenid(openid);
    		
    	}else if(from.equals("wx")){
    		
    		oneUser=accountRepository.findByWxOpenid(openid);
    		
    	}else if(from.equals("weibo")){
    		
    		oneUser=accountRepository.findByWbOpenid(openid);
    		
    	}
    	
    	return oneUser;
		
	}
	
}
