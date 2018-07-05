package com.friends.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

//交友用户举报表 投诉表
@Entity
@Table(name = "mf_user_tipoff")
public class MfUserTipoff {

	// 主键
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	private String id;
	// 举报内容
	private String content;
	// 举报上传图片
	private String image;
	// 举报人id
	private String tipOffUserId;
	// 被举报人id
	private String byTipOffUserId;
	// 举报时间
	private Date time;
	// 类型（ 1用户投诉  2平台留言）
	private String types;
	// 状态（ 1已处理  2未处理）
	private String status;
	// 备注（后台操作后向用户推送的消息内容）
	private String remark;

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getImage() {
		return image;
	}

	public void setTipOffUserId(String tipOffUserId) {
		this.tipOffUserId = tipOffUserId;
	}

	public String getTipOffUserId() {
		return tipOffUserId;
	}

	public void setByTipOffUserId(String byTipOffUserId) {
		this.byTipOffUserId = byTipOffUserId;
	}

	public String getByTipOffUserId() {
		return byTipOffUserId;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Date getTime() {
		return time;
	}

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemark() {
		return remark;
	}
}
