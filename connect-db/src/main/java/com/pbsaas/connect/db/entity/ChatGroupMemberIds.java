/**
*@Project: paintFriend_backend
*@Author: sam
*@Date: 2017年5月16日
*@Copyright: 2017  All rights reserved.
*/
package com.pbsaas.connect.db.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * @author sam
 *
 */
@Embeddable 
public class ChatGroupMemberIds  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1297928684413335794L;

	private Long group_id;
	
	private String user_id;

	public Long getGroup_id() {
		return group_id;
	}

	public void setGroup_id(Long group_id) {
		this.group_id = group_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public ChatGroupMemberIds(){
		
	}
	/**
	 * @param group_id
	 * @param user_id
	 */
	public ChatGroupMemberIds(Long group_id, String user_id) {
		super();
		this.group_id = group_id;
		this.user_id = user_id;
	}
	
	
}
