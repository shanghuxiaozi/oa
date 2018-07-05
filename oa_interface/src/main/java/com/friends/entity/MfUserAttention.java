package com.friends.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

//用户关注表
@Entity
@Table(name = "mf_user_attention")
public class MfUserAttention {

	// 用户关注表主键
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	private String id;
	// 关注用户id
	private String attentionUser;
	// 被关注用户id
	private String byTentionUser;
	// 是否成为好友（0：不是；1：是）
	private String isFriends;
	// 是否删除（0：未删除；1：删除）
	private String isDel;
	// 创建时间
	private Date createTime;
	// 是否拉黑（0：未拉；1：拉黑）
	private String isBlack;
	//
	private String isAllow;

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setAttentionUser(String attentionUser) {
		this.attentionUser = attentionUser;
	}

	public String getAttentionUser() {
		return attentionUser;
	}

	public void setByTentionUser(String byTentionUser) {
		this.byTentionUser = byTentionUser;
	}

	public String getByTentionUser() {
		return byTentionUser;
	}

	public void setIsFriends(String isFriends) {
		this.isFriends = isFriends;
	}

	public String getIsFriends() {
		return isFriends;
	}

	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}

	public String getIsDel() {
		return isDel;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setIsBlack(String isBlack) {
		this.isBlack = isBlack;
	}

	public String getIsBlack() {
		return isBlack;
	}

	public void setIsAllow(String isAllow) {
		this.isAllow = isAllow;
	}

	public String getIsAllow() {
		return isAllow;
	}
}
