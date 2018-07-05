package com.friends.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

//交友用户图片表
@Entity
@Table(name = "mf_user_image")
public class MfUserImage {

	// 主键ID
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	private String id;
	// 用户ID
	private String userId;
	// 照片名称
	private String imageName;
	// 照片类型(1.头像;2.照片,3.轮播图)
	private String imageType;
	// 创建时间
	private Date createTime;
	// 是否可用 0:不可用;1:可用
	private String isUse;
	// 轮播图链接地址
	private String connectionAddress;

	public String getConnectionAddress() {
		return connectionAddress;
	}

	public void setConnectionAddress(String connectionAddress) {
		this.connectionAddress = connectionAddress;
	}

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

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageType(String imageType) {
		this.imageType = imageType;
	}

	public String getImageType() {
		return imageType;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setIsUse(String isUse) {
		this.isUse = isUse;
	}

	public String getIsUse() {
		return isUse;
	}
}
