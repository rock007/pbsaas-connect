/**
*@Project: paintFriend_backend
*@Author: sam
*@Date: 2017年5月16日
*@Copyright: 2017  All rights reserved.
*/
package com.pbsaas.connect.db.entity;

import java.util.Date;

import javax.persistence.*;

/**
 * @author sam
 *
 */
@Entity
@Table(name = "chat_message")
public class ChatMessage {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

    @Column(name="msg_type")
	private Integer msgType;
	
	private String content;

    private String url;

    private Long group_id;
	
	private String to_user;
	
	private String send_from;
	
	private Integer status;
	
	private Date rec_date;
	
	private Date send_date;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getGroup_id() {
		return group_id;
	}

	public void setGroup_id(Long group_id) {
		this.group_id = group_id;
	}

	public String getTo_user() {
		return to_user;
	}

	public void setTo_user(String to_user) {
		this.to_user = to_user;
	}

	public String getSend_from() {
		return send_from;
	}

	public void setSend_from(String send_from) {
		this.send_from = send_from;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getRec_date() {
		return rec_date;
	}

	public void setRec_date(Date rec_date) {
		this.rec_date = rec_date;
	}

	public Date getSend_date() {
		return send_date;
	}

	public void setSend_date(Date send_date) {
		this.send_date = send_date;
	}

	public Integer getMsgType() {
		return msgType;
	}

	public void setMsgType(Integer msgType) {
		this.msgType = msgType;
	}

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
