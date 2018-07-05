package com.friends.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

//用户积分详情表
@Entity
@Table(name = "mf_integral_details")
public class MfIntegralDetails {
	// 主键Id
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	private String id;
	// 用户id
	private String userId;
	// 获取的积分
	private Integer gainIntegral;
	// 活动id
	private String activityId;
	// 创建时间
	private Date creationTime;
	// 备注
	private String remarks;

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

	public void setGainIntegral(Integer gainIntegral) {
		this.gainIntegral = gainIntegral;
	}

	public Integer getGainIntegral() {
		return gainIntegral;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getRemarks() {
		return remarks;
	}
}
