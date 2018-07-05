package com.friends.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 课堂视频表
 * 
 * @author 陈恒振 2017年12月24日下午6:22:45 TODO
 */
@Entity
@Table(name = "mf_class_mv")
public class MfClassMv implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 主键自增长
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	private String id;
	// 视频名
	private String name;
	// 视频介绍
	private String messages;
	// 视频地址
	private String address;
	// 创建时间
	private Date createDate;
	// 更新时间
	private Date updateDate;

	public MfClassMv(){
		super();
	}
	
	public MfClassMv(String id, String name, String messages, String address, Date createDate, Date updateDate,
			String person) {
		super();
		this.id = id;
		this.name = name;
		this.messages = messages;
		this.address = address;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMessages() {
		return messages;
	}

	public void setMessages(String messages) {
		this.messages = messages;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
}
