package com.friends.entity;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

// 会员风采
@Entity
@Table(name="mf_member_style")
public class MfMemberStyle{
  	//主键  唯一标识
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	private String id;
	//发布者id
	private String userId;
	//发布文字内容
	private String messages;
	//发布图片的地址
	private String imgUrl;
	//发布视频的地址
	private String mvUrl;
	//创建时间
	private Date createDate;
	//状态：1待审核 2审核通过 3审核未通过
	private String status;

	public MfMemberStyle(String id, String userId, String messages, String imgUrl, String mvUrl, Date createDate, String status) {
		super();
		this.id = id;
		this.userId = userId;
		this.messages = messages;
		this.imgUrl = imgUrl;
		this.mvUrl = mvUrl;
		this.createDate = createDate;
		this.status = status;
	}

	public MfMemberStyle() {
		super();
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
	public String getMessages() {
		return messages;
	}
	public void setMessages(String messages) {
		this.messages = messages;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getMvUrl() {
		return mvUrl;
	}
	public void setMvUrl(String mvUrl) {
		this.mvUrl = mvUrl;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getStatus() {
			return status;
		}
	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "MfMemberStyle [id=" + id + ", userId=" + userId + ", messages=" + messages + ", imgUrl=" + imgUrl
			+ ", mvUrl=" + mvUrl + ", createDate=" + createDate + "]";
	}

}

