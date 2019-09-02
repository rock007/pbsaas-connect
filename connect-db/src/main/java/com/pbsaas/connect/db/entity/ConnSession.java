/**
*@Project: paintFriend_backend
*@Author: sam
*@Date: 2017年4月10日
*@Copyright: 2017  All rights reserved.
*/
package com.pbsaas.connect.db.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author sam
 *
 */
@Entity
@Table(name = "conn_session")
public class ConnSession {

	@Id
	private String uid;
	
	@Column(name="user_id")
	private String  userId;
	
	private String ip;
	
	private String ticket;
	
	
	private Date create_date;

	//@JsonIgnore
	//@Transient
	//private ChannelHandlerContext ctx;
	
	@Transient
	private Long activeTime;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public Long getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(Long activeTime) {
		this.activeTime = activeTime;
	}
	
	
}
