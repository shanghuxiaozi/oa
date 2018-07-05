package com.friends.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

//用户信息表
@Entity
@Table(name = "mf_user_data_details")
public class MfUserDataDetails {
	// ID主键
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	private String id;
	// 用户ID
	private String userId;
	// 真实姓名
	private String realName;
	// 性别1:男2:女
	private String sex;
	// 民族
	private String nation;
	// 出生日期
	private Date birthTime;
	// 籍贯
	private String hometown;
	// 出生地
	private String birthPlace;
	// 政治面貌
	private String politicalVisage;
	// 学历
	private String education;
	// 昵称
	private String nickname;
	// 身高
	private Integer height;
	// 工作单位及职务
	private String workUnit;
	// 婚姻状况
	private String marriageStatus;
	// 有无婚史 有/无
	private String isMarriage;
	// 头像路劲
	private String headImg;
	// 生活照片，限定8张
	private String photoImg;
	// 是否公开 0不公开 1公开
	private String isOpen;
	// 身份证号码
	private String idcard;
	// 手机号码
	private String phone;
	// 毕业院校
	private String graduationSchool;
	// 专业
	private String major;
	// 学士学位
	private String baccalaureate;
	// 学历性质 统招/自考/成考/网络教育
	private String educationalType;
	// 所属行业
	private String industry;
	// 职称
	private String title;
	// 职业资格证书
	private String vocationalQualificationCertificate;
	// 年薪范围
	private String salaryRange;
	// 单位所在街道
	private String companyPosition;
	// 体重
	private String weight;
	// 生肖
	private String zdiac;
	// 微信号码
	private String wechat;
	// QQ号码
	private String qqNumber;
	// 邮箱
	private String email;
	// 星座
	private String constellation;
	// 个性标签
	private String personalityLabel;
	// 兴趣爱好
	private String hobby;
	// 择友标签
	private String friendsLabel;
	// 单位属性
	private String unitAttribute;
	// 岗位性质
	private String postNature;
	// 创建时间
	private Date creationTime;

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

	public String getHometown() {
		return hometown;
	}

	public void setHometown(String hometown) {
		this.hometown = hometown;
	}

	public String getBirthPlace() {
		return birthPlace;
	}

	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
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

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public String getWorkUnit() {
		return workUnit;
	}

	public void setWorkUnit(String workUnit) {
		this.workUnit = workUnit;
	}

	public String getMarriageStatus() {
		return marriageStatus;
	}

	public void setMarriageStatus(String marriageStatus) {
		this.marriageStatus = marriageStatus;
	}

	public String getIsMarriage() {
		return isMarriage;
	}

	public void setIsMarriage(String isMarriage) {
		this.isMarriage = isMarriage;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public String getPhotoImg() {
		return photoImg;
	}

	public void setPhotoImg(String photoImg) {
		this.photoImg = photoImg;
	}

	public String getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(String isOpen) {
		this.isOpen = isOpen;
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

	public String getGraduationSchool() {
		return graduationSchool;
	}

	public void setGraduationSchool(String graduationSchool) {
		this.graduationSchool = graduationSchool;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getBaccalaureate() {
		return baccalaureate;
	}

	public void setBaccalaureate(String baccalaureate) {
		this.baccalaureate = baccalaureate;
	}

	public String getEducationalType() {
		return educationalType;
	}

	public void setEducationalType(String educationalType) {
		this.educationalType = educationalType;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getVocationalQualificationCertificate() {
		return vocationalQualificationCertificate;
	}

	public void setVocationalQualificationCertificate(String vocationalQualificationCertificate) {
		this.vocationalQualificationCertificate = vocationalQualificationCertificate;
	}

	public String getSalaryRange() {
		return salaryRange;
	}

	public void setSalaryRange(String salaryRange) {
		this.salaryRange = salaryRange;
	}

	public String getCompanyPosition() {
		return companyPosition;
	}

	public void setCompanyPosition(String companyPosition) {
		this.companyPosition = companyPosition;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getZdiac() {
		return zdiac;
	}

	public void setZdiac(String zdiac) {
		this.zdiac = zdiac;
	}

	public String getWechat() {
		return wechat;
	}

	public void setWechat(String wechat) {
		this.wechat = wechat;
	}

	public String getQqNumber() {
		return qqNumber;
	}

	public void setQqNumber(String qqNumber) {
		this.qqNumber = qqNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getConstellation() {
		return constellation;
	}

	public void setConstellation(String constellation) {
		this.constellation = constellation;
	}

	public String getPersonalityLabel() {
		return personalityLabel;
	}

	public void setPersonalityLabel(String personalityLabel) {
		this.personalityLabel = personalityLabel;
	}

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	public String getFriendsLabel() {
		return friendsLabel;
	}

	public void setFriendsLabel(String friendsLabel) {
		this.friendsLabel = friendsLabel;
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

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
}
