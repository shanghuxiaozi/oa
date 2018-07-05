package com.friends.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

//热点推送
@Entity
@Table(name = "mf_spotpush")
public class MfSpotPush {
	// id主键
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	private String id;
	
	// 用户Id
	private String userId;
	
	//模块id,1平台介绍,2活动回顾，3交友课堂，4投诉建议，5交友活动，6我的好友
	private String moduleId;
	
	//浏览次数
	private Integer timesOfBrowsing;
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public Integer getTimesOfBrowsing() {
		return timesOfBrowsing;
	}

	public void setTimesOfBrowsing(Integer timesOfBrowsing) {
		this.timesOfBrowsing = timesOfBrowsing;
	}

	
	
	
	
	
}
