package com.friends.utils.enums;

/**
 * 举报  枚举类 
 * 名称：UserBlackList.java<br>
 * 描述：<br>
 * 类型：JAVA<br>
 * 最近修改时间: 2017年11月10日 下午2:08:00 <br>
 * @since  2017年11月9日
 * @authour wenxun
 **/
public enum UserBlackEnum {
	
	// 黑名单状态
	NO_REMOVE("0","未移出"),REMOVE("1","移出"),DELETE("3","删除");
	
	private UserBlackEnum(String code,String name){
		this.code = code;
		this.name = name;
	}
	
	private String code;
	private String name;
	
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
