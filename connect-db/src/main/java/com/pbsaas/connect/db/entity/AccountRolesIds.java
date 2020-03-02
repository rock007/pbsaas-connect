/**
*@Project: subway-backend
*@Author: sam
*@Date: 2016年12月16日
*@Copyright: 2016  All rights reserved.
*/
package com.pbsaas.connect.db.entity;

import com.pbsaas.connect.db.type.RoleType;

import java.io.Serializable;

import javax.persistence.Embeddable;



/**
 * @author sam
 *
 */

@Embeddable 
public class AccountRolesIds  implements Serializable{ 
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7896692965808334672L;

	private String user_id;
	
	private RoleType role_id;

	public RoleType getRole_id() {
		return role_id;
	}

	public void setRole_id(RoleType role_id) {
		this.role_id = role_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	@Override
	public int hashCode() {

		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {

		return super.equals(obj);
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		
		return super.clone();
	}

	@Override
	public String toString() {

		return super.toString();
	}

}
