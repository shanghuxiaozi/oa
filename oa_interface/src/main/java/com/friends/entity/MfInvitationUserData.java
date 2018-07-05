package com.friends.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

//交友项目 受邀用户
@Entity
@Table(name = "mf_invitation_user_data")
public class MfInvitationUserData {
	//
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	private String id;
	// 对应用户id
	private String userId;
	// 真实姓名
	private String realName;
	// 性别 1男 2女
	private String sex;
	// 民族
	private String nation;
	// 出生年月日
	private Date birthTime;
	// 籍贯
	private String birthplace;
	// 出生地
	private String growingPlace;
	// 政治面貌
	private String politicalVisage;
	// 学历
	private String education;
	// 婚姻状态 未婚/离异
	private String maritalStatus;
	// 身高
	private Integer height;
	// 身份证号码
	private String idcard;
	// 手机号码
	private String phone;
	// 工作单位及职务
	private String workUnit;
	// 单位属性
	private String unitAttribute;
	// 岗位性质
	private String postNature;
	
	//入职时间
	private Date startTime;
	
	//离职时间
	private Date endTime;
	
	//合同开始时间
	private Date contractStartTime;
	
	//合同结束时间
	private Date contractEndTime;
	
	//基本工资
	private BigDecimal basyPay;
	
	//是否离职1是0否
	private int isLeave;
	
	//是否在项目中1是0不是
	private int isJob;
	
	//项目名
	private String whichOne;
	
	public String getWhichOne() {
		return whichOne;
	}

	public void setWhichOne(String whichOne) {
		this.whichOne = whichOne;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getContractStartTime() {
		return contractStartTime;
	}

	public void setContractStartTime(Date contractStartTime) {
		this.contractStartTime = contractStartTime;
	}

	public Date getContractEndTime() {
		return contractEndTime;
	}

	public void setContractEndTime(Date contractEndTime) {
		this.contractEndTime = contractEndTime;
	}

	public BigDecimal getBasyPay() {
		return basyPay;
	}

	public void setBasyPay(BigDecimal basyPay) {
		this.basyPay = basyPay;
	}

	public int getIsLeave() {
		return isLeave;
	}

	public void setIsLeave(int isLeave) {
		this.isLeave = isLeave;
	}

	public int getIsJob() {
		return isJob;
	}

	public void setIsJob(int isJob) {
		this.isJob = isJob;
	}

	public String getWhich() {
		return which;
	}

	public void setWhich(String which) {
		this.which = which;
	}

	//项目名
	private String which;
	
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

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public Date getBirthTime() {
		return birthTime;
	}

	public void setBirthTime(Date birthTime) {
		this.birthTime = birthTime;
	}

	public String getBirthplace() {
		return birthplace;
	}

	public void setBirthplace(String birthplace) {
		this.birthplace = birthplace;
	}

	public String getGrowingPlace() {
		return growingPlace;
	}

	public void setGrowingPlace(String growingPlace) {
		this.growingPlace = growingPlace;
	}

	public String getPoliticalVisage() {
		return politicalVisage;
	}

	public void setPoliticalVisage(String politicalVisage) {
		this.politicalVisage = politicalVisage;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getWorkUnit() {
		return workUnit;
	}

	public void setWorkUnit(String workUnit) {
		this.workUnit = workUnit;
	}

	@Override
	public String toString() {
		return "MfInvitationUserData [id=" + id + ", userId=" + userId + ", realName=" + realName + ", sex=" + sex
				+ ", nation=" + nation + ", birthTime=" + birthTime + ", birthplace=" + birthplace + ", growingPlace="
				+ growingPlace + ", politicalVisage=" + politicalVisage + ", education=" + education
				+ ", maritalStatus=" + maritalStatus + ", height=" + height + ", idcard=" + idcard + ", phone=" + phone
				+ ", workUnit=" + workUnit + "]";
	}

	public String getUnitAttribute() {
		return unitAttribute;
	}

	public void setUnitAttribute(String unitAttribute) {
		this.unitAttribute = unitAttribute;
	}

	public String getPostNature() {
		return postNature;
	}

	public void setPostNature(String postNature) {
		this.postNature = postNature;
	}
}