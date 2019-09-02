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
public class ChatFriendIds implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 232925293118797950L;

	private String to_user;
	
	private String friend_user;

	public String getTo_user() {
		return to_user;
	}

	public void setTo_user(String to_user) {
		this.to_user = to_user;
	}

	public String getFriend_user() {
		return friend_user;
	}

	public void setFriend_user(String friend_user) {
		this.friend_user = friend_user;
	}
	
	public ChatFriendIds(){
		
	}
	
	public ChatFriendIds(String to,String from){
		to_user=to;
		friend_user=from;
	}
	
}
