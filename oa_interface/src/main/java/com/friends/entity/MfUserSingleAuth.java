package com.friends.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

//用户单身认证表
@Entity
@Table(name = "mf_user_single_auth")
public class MfUserSingleAuth {

	// 主键
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	private String id;
	// 真实姓名
	private String realName;
	// 用户ID
	private String userId;
	// 身份证号码
	private String idcard;
	// 身份证照片
	private String idcardImg;
	// 户口本照片
	private String residenceImg;
	// 认证结果 0:待审核 1:审核通过 2:审核未通过
	private String authResult;
	// 创建时间
	private Date creationTime;
	// 对应工会(单位)ID
	private String labourUnionId;
	// 认证类型 1:受邀认证 2:工会认证 3:自由认证
	private String authType;

	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getRealName() {
		return realName;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcardImg(String idcardImg) {
		this.idcardImg = idcardImg;
	}

	public String getIdcardImg() {
		return idcardImg;
	}

	public void setResidenceImg(String residenceImg) {
		this.residenceImg = residenceImg;
	}

	public String getResidenceImg() {
		return residenceImg;
	}

	public void setAuthResult(String authResult) {
		this.authResult = authResult;
	}

	public String getAuthResult() {
		return authResult;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public Date getCreationTime() {
		return creationTime;
	}
	public String getLabourUnionId() {
		return labourUnionId;
	}
	public void setLabourUnionId(String labourUnionId) {
		this.labourUnionId = labourUnionId;
	}
	public String getAuthType() {
		return authType;
	}
	public void setAuthType(String authType) {
		this.authType = authType;
	}
	
}
