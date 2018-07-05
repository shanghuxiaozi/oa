package com.friends.entity.vo;

import com.friends.entity.Pay;


public class PayDynamicQueryVo extends BaseVo<Pay> implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private String queryDate;
	public String getQueryDate() {
		return queryDate;
	}
	public void setQueryDate(String queryDate) {
		this.queryDate = queryDate;
	}
	
	
}

