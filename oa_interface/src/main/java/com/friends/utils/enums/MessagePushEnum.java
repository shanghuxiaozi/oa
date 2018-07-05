package com.friends.utils.enums;

/**
 * 消息推送  枚举类
 * 名称：MessagePushEnum.java<br>
 * 描述：<br>
 * 类型：JAVA<br>
 * 最近修改时间: 2017年11月9日 下午3:03:51 <br>
 * @since  2017年11月9日
 * @authour wenxun
 */
public enum MessagePushEnum {
	
	// 消息类型	
	SYSTEM_MESSAGE("1","系统推送消息"),ACTIVITY_MESSAGE("2","活动推送消息"),MAIL_ANGLE_MESSAGE("3","点对点站内信"),MAIL_MESSAGE("4","面对所有人站内信"),
	// 是否读取
	UNREAD("0","未读"),READ("1","已读"),
	// 是否新用户须知消息
	YES("1","是"),NO("0","不是");
	
	private String code;
	private String name;
	
	private MessagePushEnum(String code,String name){
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
