package com.pbsaas.connect.db.repository;

import com.pbsaas.connect.db.entity.AccountRoles;
import com.pbsaas.connect.db.entity.AccountRolesIds;
import org.springframework.data.repository.CrudRepository;


public interface AccountRolesRepository extends CrudRepository<AccountRoles, AccountRolesIds> {
	
}