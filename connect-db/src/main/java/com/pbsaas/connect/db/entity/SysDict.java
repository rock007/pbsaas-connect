package com.pbsaas.connect.db.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 数据字典
 * 
 * @author 
 *
 */
@Entity
@Table(name = "sys_dict")
public class SysDict {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Long mtype;
	
	@NotNull
	@Size(min=1,max=20)
	private String mkey;
	
	@NotNull
	@Size(min=1, max=50)
	private String text;
	private Long flag;
	private Date create_date;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMtype() {
		return mtype;
	}

	public void setMtype(Long mtype) {
		this.mtype = mtype;
	}

	public String getMkey() {
		return mkey;
	}

	public void setMkey(String mkey) {
		this.mkey = mkey;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Long getFlag() {
		return flag;
	}

	public void setFlag(Long flag) {
		this.flag = flag;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

}
