package com.friends.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

//消息推送表
@Entity
@Table(name = "mf_message_push")
public class MfMessagePush {
	// 主键
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	private String id;
	// 用户id
	private String userId;
	// 创建时间
	private Date creatTime;
	// 状态（0：未读；1：已读）
	private String type;
	// 推送消息内容
	private String messageContent;
	// 消息类型（1：系统消息；2：活动消息）
	private String messageType;
	// 是否是新用户须知消息（0：不是；1：是；）
	private String isNewuserTips;
	// 其他
	private String other;

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

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

	public Date getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getIsNewuserTips() {
		return isNewuserTips;
	}

	public void setIsNewuserTips(String isNewuserTips) {
		this.isNewuserTips = isNewuserTips;
	}

}
