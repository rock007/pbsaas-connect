package com.pbsaas.connect.db.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "sys_log")
public class ActLog {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Long id;
	
	private String title;
	
	private String msg;
	
	private String content;
	
	private String error;
	
	private String create_user;
	
	private String create_ip;
	
	private Date create_date;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getCreate_user() {
		return create_user;
	}

	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}

	public String getCreate_ip() {
		return create_ip;
	}

	public void setCreate_ip(String create_ip) {
		this.create_ip = create_ip;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	
	public ActLog() {
		super();
	}

	/***
	 * 
	 * @param title
	 * @param msg
	 * @param content
	 * @param error
	 * @param create_user
	 * @param create_ip
	 */
	public ActLog(String title, String msg, String content, String error, String create_user, String create_ip) {
		super();
		this.title = title;
		this.msg = msg;
		this.content = content;
		this.error = error;
		this.create_user = create_user;
		this.create_ip = create_ip;
		this.create_date=new Date();
	}
	
}