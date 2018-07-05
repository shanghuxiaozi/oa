package com.friends.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;


//交友活动发布表
@Entity
@Table(name = "mf_activity_release")
public class MfActivityRelease {
	// 主键自增长
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	private String id;
	// 活动标题
	private String title;
	// 活动内容(活动介绍)
	private String content;
	// 活动发布时间
	private Date time;
	// 活动发起人
	private String authority;
	// 活动开始时间
	private Date activityStartTime;
	// 活动结束时间
	private Date activityEndTime;
	// 报名开始时间
	private Date applyStartTime;
	// 报名结束时间
	private Date applyEndTime;
	// 报名人数
	private Integer applyNumber;
	// 活动条件
	private String activityTerm;
	// 活动流程
	private String activityProgress;
	// 活动地点
	private String activityAddress;
	// 联系方式
	private String activityPhone;
	// 活动注意事项
	private String activityAttentionMatter;
	// 活动状态
	private String activityStatus;
	// 创建时间
	private Date createTime;
	// 修改时间
	private Date updateTime;
	// 所需信誉分(参加活动最低信誉分数)
	private Integer requiredCredit;
	// 奖励信誉分
	private Integer rewardCredit;
	// 参与活动奖励积分
	private Integer rewardIntegral;
	// 活动图片链接
	private String activityImage;
	// 活动地址经度
	private String addressLongitude;
	// 活动地址纬度
	private String addressLatitude;
	// 是否删除(0:未删除;1已删除:)
	private Integer isDelete;
	// 主办单位
	private String sponsor;
	// 活动内容图片
	private String contentImg;
	// 富文本图片上传所需参数
	private String code;
	// 0:表示单身活动；2：普通活动
	private String type;

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Date getTime() {
		return time;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public String getAuthority() {
		return authority;
	}

	public void setActivityStartTime(Date activityStartTime) {
		this.activityStartTime = activityStartTime;
	}

	public Date getActivityStartTime() {
		return activityStartTime;
	}

	public void setActivityEndTime(Date activityEndTime) {
		this.activityEndTime = activityEndTime;
	}

	public Date getActivityEndTime() {
		return activityEndTime;
	}

	public void setApplyStartTime(Date applyStartTime) {
		this.applyStartTime = applyStartTime;
	}

	public Date getApplyStartTime() {
		return applyStartTime;
	}

	public void setApplyEndTime(Date applyEndTime) {
		this.applyEndTime = applyEndTime;
	}

	public Date getApplyEndTime() {
		return applyEndTime;
	}

	public void setApplyNumber(Integer applyNumber) {
		this.applyNumber = applyNumber;
	}

	public Integer getApplyNumber() {
		return applyNumber;
	}

	public void setActivityTerm(String activityTerm) {
		this.activityTerm = activityTerm;
	}

	public String getActivityTerm() {
		return activityTerm;
	}

	public void setActivityProgress(String activityProgress) {
		this.activityProgress = activityProgress;
	}

	public String getActivityProgress() {
		return activityProgress;
	}

	public void setActivityAddress(String activityAddress) {
		this.activityAddress = activityAddress;
	}

	public String getActivityAddress() {
		return activityAddress;
	}

	public void setActivityPhone(String activityPhone) {
		this.activityPhone = activityPhone;
	}

	public String getActivityPhone() {
		return activityPhone;
	}

	public void setActivityAttentionMatter(String activityAttentionMatter) {
		this.activityAttentionMatter = activityAttentionMatter;
	}

	public String getActivityAttentionMatter() {
		return activityAttentionMatter;
	}

	public void setActivityStatus(String activityStatus) {
		this.activityStatus = activityStatus;
	}

	public String getActivityStatus() {
		return activityStatus;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setRequiredCredit(Integer requiredCredit) {
		this.requiredCredit = requiredCredit;
	}

	public Integer getRequiredCredit() {
		return requiredCredit;
	}

	public void setRewardCredit(Integer rewardCredit) {
		this.rewardCredit = rewardCredit;
	}

	public Integer getRewardCredit() {
		return rewardCredit;
	}

	public void setRewardIntegral(Integer rewardIntegral) {
		this.rewardIntegral = rewardIntegral;
	}

	public Integer getRewardIntegral() {
		return rewardIntegral;
	}

	public void setActivityImage(String activityImage) {
		this.activityImage = activityImage;
	}

	public String getActivityImage() {
		return activityImage;
	}

	public void setAddressLongitude(String addressLongitude) {
		this.addressLongitude = addressLongitude;
	}

	public String getAddressLongitude() {
		return addressLongitude;
	}

	public void setAddressLatitude(String addressLatitude) {
		this.addressLatitude = addressLatitude;
	}

	public String getAddressLatitude() {
		return addressLatitude;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setSponsor(String sponsor) {
		this.sponsor = sponsor;
	}

	public String getSponsor() {
		return sponsor;
	}

	public void setContentImg(String contentImg) {
		this.contentImg = contentImg;
	}

	public String getContentImg() {
		return contentImg;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}
