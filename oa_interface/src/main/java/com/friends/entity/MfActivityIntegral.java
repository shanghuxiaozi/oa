package com.friends.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

//交友活动积分表
@Entity
@Table(name = "mf_activity_integral")
public class MfActivityIntegral {
	// ID主键
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	private String id;
	// 用户id
	private String userId;
	// 我的积分
	private Integer integral;
	// 用户信誉积分
	private Integer point;

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

	public void setIntegral(Integer integral) {
		this.integral = integral;
	}

	public Integer getIntegral() {
		return integral;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	public Integer getPoint() {
		return point;
	}
}
