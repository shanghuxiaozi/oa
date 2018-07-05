package com.friends.utils.enums;

/**
 * 单身认证  枚举类
 * 名称：SingleAuthEnum.java<br>
 * 描述：<br>
 * 类型：JAVA<br>
 * 最近修改时间: 2017年10月26日 下午3:32:12 <br>
 * @since  2017年10月26日
 * @authour wenxun
 */
public enum SingleAuthEnum {
	
	PENDING_AUDIT("0","待审核"),AUDIT_PASSED("1","审核通过"),AUDIT_NOT_THROUGH("2","审核不通过");
	
	private String code;
	private String name;
	
	private SingleAuthEnum(String code,String name){
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
