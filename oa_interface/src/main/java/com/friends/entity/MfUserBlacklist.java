package com.friends.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

//交友用户黑名单表
@Entity
@Table(name = "mf_user_blacklist")
public class MfUserBlacklist {

	// 主键
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	private String id;
	// 用户id
	private String userId;
	// 被拉黑用户id
	private String byUserId;
	// 类型（1：好友；2：非好友）
	private String type;
	// 创建时间
	private Date createTime;
	// 是否删除（0：未删除；1：删除）
	private String isDel;
	// 是否移出（0：未移出；1：移出）
	private String isRemove;

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}

	public void setByUserId(String byUserId) {
		this.byUserId = byUserId;
	}

	public String getByUserId() {
		return byUserId;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}

	public String getIsDel() {
		return isDel;
	}

	public void setIsRemove(String isRemove) {
		this.isRemove = isRemove;
	}

	public String getIsRemove() {
		return isRemove;
	}
}
