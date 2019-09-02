package com.pbsaas.connect.db.entity.cms;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cms_comments")
public class Comment {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private Integer ref_type;
	
	private Long ref_id;
	
	private String content;
	
	@Column(name = "from_user")
	private String fromUser;
	
	@Column(name = "reply_user")
	private String replyUser;
	
	private Date create_date;

	private String create_ip;

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

	public String getFromUser() {
		return fromUser;
	}

	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}

	public String getReplyUser() {
		return replyUser;
	}

	public void setReplyUser(String replyUser) {
		this.replyUser = replyUser;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public String getCreate_ip() {
		return create_ip;
	}

	public void setCreate_ip(String create_ip) {
		this.create_ip = create_ip;
	}

	public Integer getRef_type() {
		return ref_type;
	}

	public void setRef_type(Integer ref_type) {
		this.ref_type = ref_type;
	}

	public Long getRef_id() {
		return ref_id;
	}

	public void setRef_id(Long ref_id) {
		this.ref_id = ref_id;
	}
}
