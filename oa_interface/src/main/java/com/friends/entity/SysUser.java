package com.friends.entity;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * 用户信息
 * 
 * @author lzl 2017-8-22
 */
@Entity
@Table(name = "sys_user")
public class SysUser implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	private String id; // 编号

	@Column(unique = true, name = "user_name", nullable = false,length=30)
	private String userName; // 用户名

	@Column(name = "real_name")
	private String realName;// 真实姓名

	private String nickname;// 昵称

	@Column(nullable = false)
	private String password; // 密码

	@Column(length = 60)
	private String image;// 头像

	private Boolean sex;// 性别
	@Temporal(TemporalType.DATE)
	private Date birthday;// 生日

	@Column(length = 50)
	private String address;// 居住地

	private String salt; // 加密密码的盐

	private Integer locked;// 用户状态 0:创建未认证（比如没有激活，没有输入验证码等等）--等待验证的用户 ,
							// 1:正常状态,2：用户被锁定.
	private String email;// 邮箱

	private String phone;// 电话
	
	private String labourUnionId;  // 工会Id

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date")
	private Date createDate;// 创建时间/注册时间
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "pre_login_date")
	private Date preLoginDate;// 上一次登录时间

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_date")
	private Date updateDate;// 修改时间

	private Integer points;// 积分数

	private Integer grade;// 等级
	@Column(length = 200)
	private String introduce;// 介绍

	@Column(name = "fan_count")
	private Integer fanCount;// 粉丝数

	@Column(name = "attent_count")
	private Integer attentCount;// 关注数

	@Column(name = "group_count")
	private Integer groupCount;// 群组数

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Boolean getSex() {
		return sex;
	}

	public void setSex(Boolean sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Integer getLocked() {
		return locked;
	}

	public void setLocked(Integer locked) {
		this.locked = locked;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


	public Date getPreLoginDate() {
		return preLoginDate;
	}

	public void setPreLoginDate(Date preLoginDate) {
		this.preLoginDate = preLoginDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public Integer getFanCount() {
		return fanCount;
	}

	public void setFanCount(Integer fanCount) {
		this.fanCount = fanCount;
	}

	public Integer getAttentCount() {
		return attentCount;
	}

	public void setAttentCount(Integer attentCount) {
		this.attentCount = attentCount;
	}

	public Integer getGroupCount() {
		return groupCount;
	}

	public void setGroupCount(Integer groupCount) {
		this.groupCount = groupCount;
	}

	/**
	 * 密码盐.
	 * @return
	 */
	public String getCredentialsSalt(){
		return this.userName+this.salt;
	}

	public String getLabourUnionId() {
		return labourUnionId;
	}

	public void setLabourUnionId(String labourUnionId) {
		this.labourUnionId = labourUnionId;
	}
	
}
