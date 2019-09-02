/**
*@Project: paintFriend_backend
*@Author: sam
*@Date: 2017年5月16日
*@Copyright: 2017  All rights reserved.
*/
package com.pbsaas.connect.db.entity;

import java.util.Date;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author sam
 *
 */
@Entity
@Table(name = "chat_friend")
public class ChatFriend {

	@EmbeddedId 
	private ChatFriendIds ids;
	
	private String nick_name;
	
	private Date accept_date;
	
	private Integer req_status;
	
	private Date create_date;

	public ChatFriendIds getIds() {
		return ids;
	}

	public void setIds(ChatFriendIds ids) {
		this.ids = ids;
	}

	public String getNick_name() {
		return nick_name;
	}

	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}

	public Date getAccept_date() {
		return accept_date;
	}

	public void setAccept_date(Date accept_date) {
		this.accept_date = accept_date;
	}

	public Integer getReq_status() {
		return req_status;
	}

	public void setReq_status(Integer req_status) {
		this.req_status = req_status;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	
}
