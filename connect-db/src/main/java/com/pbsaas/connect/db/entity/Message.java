package com.pbsaas.connect.db.entity;

import com.pbsaas.connect.db.type.MessageType;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * 
 * @author 
 *
 */
@Entity
@Table(name = "sys_message")
public class Message {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	 private Long  id;
	
	 @NotNull
	 private MessageType msg_type;//消息类型
	 
	 @NotNull
	 @Size(min=5,max=512)
	 private String content;//消息内容
	 private Integer status;//状态  0 未读  1 已读
	 private String send_to; //接受者
	 private String send_from;//发送者
	 private Date create_date;//创建时间
	 private Date read_date; //阅读时间
	 
	public Message() {
		super();
	}
	
	public Message(MessageType msg_type, String content, String send_to, String send_from) {
		super();
		this.msg_type = msg_type;
		this.content = content;
		this.status = 0;
		this.send_to = send_to;
		this.send_from = send_from;
		this.create_date=new Date();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public MessageType getMsg_type() {
		return msg_type;
	}
	public void setMsg_type(MessageType msg_type) {
		this.msg_type = msg_type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getSend_to() {
		return send_to;
	}
	public void setSend_to(String send_to) {
		this.send_to = send_to;
	}
	public String getSend_from() {
		return send_from;
	}
	public void setSend_from(String send_from) {
		this.send_from = send_from;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	public Date getRead_date() {
		return read_date;
	}
	public void setRead_date(Date read_date) {
		this.read_date = read_date;
	}
	 
	 
	 
	 
 
}
