package com.friends.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

//热门头条表
@Entity
@Table(name = "mf_headlines")
public class MfHeadlines {
	// id主键
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	private String id;
	// 头条标题
	private String title;
	
	// 头条内容
	private String content;
	// 头条时间
	private Date headTime;
	// 头条图片链接地址
	private String headImage;
	
	//是否显示类型,0显示，1不显示
	private String type;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getHeadTime() {
		return headTime;
	}
	public void setHeadTime(Date headTime) {
		this.headTime = headTime;
	}
	public String getHeadImage() {
		return headImage;
	}
	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}
}
