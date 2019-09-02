/**
*@Project: subway-backend
*@Author: sam
*@Date: 2016年12月16日
*@Copyright: 2016  All rights reserved.
*/
package com.pbsaas.connect.db.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author sam
 *
 */
@Entity
@Table(name = "sys_user_role")
public class AccountRoles {

	@EmbeddedId 
	AccountRolesIds accountRolesIds;

	private String authority_data;
	
	public AccountRolesIds getAccountRolesIds() {
		return accountRolesIds;
	}

	public void setAccountRolesIds(AccountRolesIds accountRolesIds) {
		this.accountRolesIds = accountRolesIds;
	}

	public String getAuthority_data() {
		return authority_data;
	}

	public void setAuthority_data(String authority_data) {
		this.authority_data = authority_data;
	}
	
}

