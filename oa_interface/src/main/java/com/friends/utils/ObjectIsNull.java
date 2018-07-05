package com.friends.utils;

import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

/**
 * 判断是否为空类
 * 
 * @return true:为空 false:不为空
 * @author pengyl
 */
public class ObjectIsNull {
	/**
	 * 判断List集合是否为空
	 * 
	 * @param list
	 * @return
	 */
	public static <T> boolean list(List<T> list) {
		if (list == null || list.size() <= 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断Map是否为空
	 * 
	 * @param map
	 * @return
	 */
	public static <T> boolean map(Map<String, T> map) {
		if (map == null || map.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean string(String str) {
		if (StringUtils.isEmpty(str)) {
			return true;
		} else if (str.equals("null") || str.equals("undefined")) {
			return true;
		} else {
			return false;
		}
	}

}
