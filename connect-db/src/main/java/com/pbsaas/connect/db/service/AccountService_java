/**
*@Project: subway-backend
*@Author: sam
*@Date: 2016年12月12日
*@Copyright: 2016  All rights reserved.
*/
package com.pbsaas.connect.db.service;

import java.util.List;

import com.pbsaas.connect.db.type.RoleType;
import com.pbsaas.connect.db.entity.Account;
import com.pbsaas.connect.db.entity.AccountRoles;
import org.springframework.data.domain.Page;

/**
 * @author sam
 *
 */
public interface AccountService {

	public List<Account> findAll();
	
	public Account findById(String id);
	
	public Account findByName(String name);
	
	public Account findByMobile(String phone);
	
	public Account save(Account m);
	
	public void delete(String id);
	
	public Page<Account> search(Account m, int page, int pageSize);
	
	public AccountRoles findRole(String user_id, RoleType role_id);
	
	public void addRole(String user_id, RoleType role_id);
	
	public void delRoles(String user_id,RoleType role_id);
	
	public Account find4Third(String from,String openid);
}
