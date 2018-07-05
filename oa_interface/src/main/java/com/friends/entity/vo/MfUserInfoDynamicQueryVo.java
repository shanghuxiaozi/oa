package com.friends.entity.vo;

import java.util.Date;

import com.friends.entity.MfUserInfo;

public class MfUserInfoDynamicQueryVo extends BaseVo<MfUserInfo> implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// 查询参数
	/** 积分范围 **/ // 查询条件
	private Integer integralStart;
	private Integer integralEnd;

	// 返回结果参数
	/** 返回结果 **/
	private Integer integral;

	public Integer getIntegralStart() {
		return integralStart;
	}

	public void setIntegralStart(Integer integralStart) {
		this.integralStart = integralStart;
	}

	public Integer getIntegralEnd() {
		return integralEnd;
	}

	public void setIntegralEnd(Integer integralEnd) {
		this.integralEnd = integralEnd;
	}

	public Integer getIntegral() {
		return integral;
	}

	public void setIntegral(Integer integral) {
		this.integral = integral;
	}

}
