package com.friends.utils.enums;

/**
 * 用户类型 枚举类<br>
 * 名称：UserTypeEnum.java<br>
 * 描述：<br>
 * 类型：JAVA<br>
 * 最近修改时间: 2017年10月17日 下午3:53:53 <br>
 * @since  2017年10月17日
 * @authour wenxun
 */
public enum UserTypeEnum {
	// 用户类型
	REGISTER_USER("1","注册用户"),SINGLE_USER("2","单身用户"),MEMBER_USER("3","会员用户"),CLOSE_USER("4","封号用户"),
	// 用户性别
	SEX_MALE("1","男"),SEX_FEMALE("2","女"),
	// 用户状态 
	REGISTER("1","注册"),AUTH("2","认证"),PERFECT_DATA("3","完善资料"),CLOSE("4","封号");
	
	private String code;
	private String name;
	
	private UserTypeEnum(String code,String name){
		this.code = code;
		this.name = name();
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
