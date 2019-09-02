/**
*@Project: paintFriend_backend
*@Author: sam
*@Date: 2017年5月16日
*@Copyright: 2017  All rights reserved.
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
@Table(name = "chat_group_member")
public class ChatGroupMember {

	@EmbeddedId 
	private ChatGroupMemberIds ids;
	
	private String nick_name;

	public ChatGroupMemberIds getIds() {
		return ids;
	}

	public void setIds(ChatGroupMemberIds ids) {
		this.ids = ids;
	}

	public String getNick_name() {
		return nick_name;
	}

	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}
	
}
