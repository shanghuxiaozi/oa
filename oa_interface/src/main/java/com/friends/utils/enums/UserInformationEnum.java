package com.friends.utils.enums;

/**
 * 用户资料，枚举类
 * 名称：UserInformationEnum.java<br>
 * 描述：<br>
 * 类型：JAVA<br>
 * 最近修改时间: 2017年11月13日 上午10:53:49 <br>
 * @since  2017年11月13日
 * @authour wenxun
 */
public enum UserInformationEnum {
	
	MALE("1","男"),FLMALE("2","女"),
	DOCTOR("1","博士"),MASTER("2","硕士"),UNDERGRADUATE("3","本科"),
	JUNIOR_COLLEGE("4","大专"),FOLLOWING_COLLEGE("5","大专以下"),
	HAVE_HOUSE_HAVE_CAR("1","有房有车"),HAVE_HOUSE_NOT_CAR("2","有房无车"), NOT_HOUSE_HAVE_CAR("3","无房有车"), NOT_HOUSE_NOT_CAR("4","无房无车")
	;
	
	private String code;
	private String name;
	
	private UserInformationEnum(String code,String name){
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
