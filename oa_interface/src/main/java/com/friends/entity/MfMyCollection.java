package com.friends.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

//我的收藏  表
@Entity
@Table(name = "mf_my_collection")
public class MfMyCollection {
	// ID主键
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	private String id;
	// 用户Id
	private String userId;
	// 活动Id
	private String activityId;
	// 视频Id 除了收藏活动，交友课堂里面的视频也可以收藏
	private String videoId;
	// 其他Id 预留字段，可能会新增收藏项
	private String otherId;
	// 收藏时间
	private Date collectionTime;

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

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	public String getVideoId() {
		return videoId;
	}

	public void setOtherId(String otherId) {
		this.otherId = otherId;
	}

	public String getOtherId() {
		return otherId;
	}

	public void setCollectionTime(Date collectionTime) {
		this.collectionTime = collectionTime;
	}

	public Date getCollectionTime() {
		return collectionTime;
	}
}
