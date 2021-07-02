package com.pbsaas.connect.db.repository;

import com.pbsaas.connect.db.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface AccountRepository extends JpaRepository<Account, String> ,JpaSpecificationExecutor<Account>{
	
	Account findByUserName(String username);
	
	Account findByMobile(String mobile);
	
	Account findByWxOpenid(String open_id);
	
	Account findByQqOpenid(String open_id);
	
	Account findByWbOpenid(String open_id);

	int countByMobile(String mobile);

	int countByUserName(String userName);
}