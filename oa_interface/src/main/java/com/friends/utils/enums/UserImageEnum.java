package com.friends.utils.enums;

/**
 * 用户图片 枚举类
 * 名称：UserImageEnum.java<br>
 * 描述：<br>
 * 类型：JAVA<br>
 * 最近修改时间: 2017年11月4日 下午1:46:42 <br>
 * @since  2017年11月4日
 * @authour wenxun
 */
public enum UserImageEnum {
	
	// 图片类型
	HEAD_PORTRAIT("1","头像"),PHOTO("2","照片"),CAROUSEL_IMAGE("3","轮播图"),
	// 图片状态
	MAY_NOT("0","不可用"),SURE("1","可用");
	
	private String code;
	private String name;
	
	private UserImageEnum(String code,String name){
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
