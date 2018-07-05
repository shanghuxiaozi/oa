package com.friends.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

//活动回顾表
@Entity
@Table(name = "mf_activity_review")
public class MfActivityReview {
	// id主键
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	private String id;
	// 活动标题
	private String title;
	// 活动内容
	private String content;
	// 活动时间
	private Date activityTime;
	// 活动图片链接地址
	private String activityImage;

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setActivityTime(Date activityTime) {
		this.activityTime = activityTime;
	}

	public Date getActivityTime() {
		return activityTime;
	}

	public void setActivityImage(String activityImage) {
		this.activityImage = activityImage;
	}

	public String getActivityImage() {
		return activityImage;
	}
}
