package com.friends.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

//交友活动报名表
@Entity
@Table(name = "mf_activity_enroll")
public class MfActivityEnroll {
	// 主键ID
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	private String id;
	// 用户ID
	private String userId;
	// 活动ID
	private String activityId;
	// 创建时间
	private Date createTime;
	// 修改时间
	private Date updateTime;
	// 是否参加(0:参加;1:退出)
	private String isJoin;
	// 活动状态：0：待定/1：已到/2：未到
	private String type;

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

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setIsJoin(String isJoin) {
		this.isJoin = isJoin;
	}

	public String getIsJoin() {
		return isJoin;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}
