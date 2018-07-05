package com.friends.entity.vo;

import com.friends.entity.MfActivityRelease;

public class MfActivityReleaseDynamicQueryVo extends BaseVo<MfActivityRelease> implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// 活动已报名人数
	private Integer toApplyNumber;

	public Integer getToApplyNumber() {
		return toApplyNumber;
	}

	public void setToApplyNumber(Integer toApplyNumber) {
		this.toApplyNumber = toApplyNumber;
	}
}
