package com.friends.entity.vo;

import com.friends.entity.MfUserDataDetails;

public class MfUserDataDetailsDynamicQueryVo extends BaseVo<MfUserDataDetails> implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	// 用户头像路径
	private String imageName;
	//用户姓名
	private String name;
	// 用户昵称
	private String nickname;
	// 用户性别
	private String sex;
	// 用户身份证号
	private String IDcard;
	//用户手机号
	private String phone;
	//婚姻状况
	private String maritalStatus;
	
	// 筛选用户 参数
	// 年龄段
	private String startAge;
	private String endAge;
	// 身高段
	private String startHeight;
	private String endHeight;
	// 省份
	private String province;
	// 学历
	private String diploma;
	// 职业
	private String profession;
	// 单位属性
	private String unitAttribute;
	// 岗位性质
	private String postNature;

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getIDcard() {
		return IDcard;
	}

	public void setIDcard(String iDcard) {
		IDcard = iDcard;
	}
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getStartAge() {
		return startAge;
	}

	public void setStartAge(String startAge) {
		this.startAge = startAge;
	}

	public String getEndAge() {
		return endAge;
	}

	public void setEndAge(String endAge) {
		this.endAge = endAge;
	}

	public String getStartHeight() {
		return startHeight;
	}

	public void setStartHeight(String startHeight) {
		this.startHeight = startHeight;
	}

	public String getEndHeight() {
		return endHeight;
	}

	public void setEndHeight(String endHeight) {
		this.endHeight = endHeight;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getDiploma() {
		return diploma;
	}

	public void setDiploma(String diploma) {
		this.diploma = diploma;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
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
