package com.friends.utils;

public class RemindInfo {

	private String orderId;
	private String orderName;
	private String mobilePhone;

	public RemindInfo() {
		super();
	}

	public RemindInfo(String orderId, String orderName, String mobilePhone) {
		super();
		this.orderId = orderId;
		this.orderName = orderName;
		this.mobilePhone = mobilePhone;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
}
