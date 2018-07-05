package com.friends.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

//网站用户表
@Entity
@Table(name = "mf_user_info")
public class MfUserInfo {

	// 用户ID主键
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	private String id;
	// 用户手机号，登录名
	private String phone;
	// 微信公众号用户的唯一的标识
	private String openid;
	// 用户密码
	private String password;
	// 用户名
	private String name;
	// 性别
	private String sex;
	// 昵称
	private String nickname;
	// 注册时间
	private Date createTime;
	// 1:普通用户 /2:单身用户/3:会员用户/4:封号用户
	private String userType;
	// 环信唯一标识
	private String huanxinFlag;
	//状态 1注册 2认证 3完善资料
	private String status;

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhone() {
		return phone;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getOpenid() {
		return openid;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getSex() {
		return sex;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getNickname() {
		return nickname;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUserType() {
		return userType;
	}

	public void setHuanxinFlag(String huanxinFlag) {
		this.huanxinFlag = huanxinFlag;
	}

	public String getHuanxinFlag() {
		return huanxinFlag;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
