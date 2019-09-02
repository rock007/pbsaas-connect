package com.pbsaas.connect.db.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "sys_user")
public class Account {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;
	
	@Column(name="account_name")
	private String name;
	@JsonIgnore
	private String pwd;	
	private String mobile;
	
	private Integer sex;
	
	private String province;
	
	private String city;
	
	private Integer user_level;
	
	private Float user_money;
	
	private String avatar;
	
	@JsonIgnore
	private Date create_date;
	
	private Date last_date;
	
	private String last_ipaddress;

	@Column(name="wx_openid")
	private String wxOpenid;
	private String wx_token;
	private String wx_nickname;
	
	@Column(name="qq_openid")
	private String qqOpenid;
	private String qq_token;
	private String qq_nickname;
	
	@Column(name="wb_openid")
	private String wbOpenid;
	private String wb_token;
	private String wb_nickname;
	
	private Integer status;
	
	private String birthday;
	
	private String nickname;

    @JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="sys_user_role",
		joinColumns={@JoinColumn(name="user_id", referencedColumnName="id")},
		inverseJoinColumns={@JoinColumn(name="role_id", referencedColumnName="id")})
	Set<Role> roles = new HashSet<Role>();
	
	private String reg_from;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getUser_level() {
		return user_level;
	}

	public void setUser_level(Integer user_level) {
		this.user_level = user_level;
	}

	

	public Float getUser_money() {
		return user_money;
	}

	public void setUser_money(Float user_money) {
		this.user_money = user_money;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public Date getLast_date() {
		return last_date;
	}

	public void setLast_date(Date last_date) {
		this.last_date = last_date;
	}

	public String getLast_ipaddress() {
		return last_ipaddress;
	}

	public void setLast_ipaddress(String last_ipaddress) {
		this.last_ipaddress = last_ipaddress;
	}


	public String getWx_token() {
		return wx_token;
	}

	public void setWx_token(String wx_token) {
		this.wx_token = wx_token;
	}

	public String getWx_nickname() {
		return wx_nickname;
	}

	public void setWx_nickname(String wx_nickname) {
		this.wx_nickname = wx_nickname;
	}

	public String getQq_token() {
		return qq_token;
	}

	public void setQq_token(String qq_token) {
		this.qq_token = qq_token;
	}

	public String getQq_nickname() {
		return qq_nickname;
	}

	public void setQq_nickname(String qq_nickname) {
		this.qq_nickname = qq_nickname;
	}

	public String getWb_token() {
		return wb_token;
	}

	public void setWb_token(String wb_token) {
		this.wb_token = wb_token;
	}

	public String getWb_nickname() {
		return wb_nickname;
	}

	public void setWb_nickname(String wb_nickname) {
		this.wb_nickname = wb_nickname;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}


	public String getWxOpenid() {
		return wxOpenid;
	}

	public void setWxOpenid(String wxOpenid) {
		this.wxOpenid = wxOpenid;
	}

	public String getQqOpenid() {
		return qqOpenid;
	}

	public void setQqOpenid(String qqOpenid) {
		this.qqOpenid = qqOpenid;
	}

	public String getWbOpenid() {
		return wbOpenid;
	}

	public void setWbOpenid(String wbOpenid) {
		this.wbOpenid = wbOpenid;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getReg_from() {
		return reg_from;
	}

	public void setReg_from(String reg_from) {
		this.reg_from = reg_from;
	}
	
	
}