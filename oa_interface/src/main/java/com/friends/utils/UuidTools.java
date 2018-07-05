package com.friends.utils;

import java.util.UUID;

/**
 * 主键生成器
 * 
 * @author pengyl 2016年6月20日
 */
public class UuidTools {

	/**
	 * 生成uuid主键
	 * 
	 * @return
	 */
	public static String createUuid() {
		String uuid = UUID.randomUUID().toString();
		String id = uuid.replace("-", "");
		return id;
	}

	/**
	 * 生成二维码编号
	 */
	public static String createQRCodeId(int count) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < count; i++) {
			sb.append((int) (10 * (Math.random())));
		}
		return sb.toString();
	}
}
