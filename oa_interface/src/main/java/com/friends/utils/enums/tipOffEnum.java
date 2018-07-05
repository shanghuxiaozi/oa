package com.friends.utils.enums;

/**
 * 举报  枚举类 
 * 名称：tipOffEnum.java<br>
 * 描述：<br>
 * 类型：JAVA<br>
 * 最近修改时间: 2017年11月9日 下午2:56:00 <br>
 * @since  2017年11月9日
 * @authour wenxun
 */
public enum tipOffEnum {
	
	// 举报状态
	ALREADY_PROCESSED("1","已处理"),UNTREATED("2","未处理");
	
	private String code;
	private String name;
	
	private tipOffEnum(String code,String name){
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
