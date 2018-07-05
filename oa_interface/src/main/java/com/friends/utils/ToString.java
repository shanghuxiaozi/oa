package com.friends.utils;

public class ToString {

	public static String objToStr(Object obj) {
		if (obj == null) {
			return "";
		} else if (obj.toString().equals("null") || obj.toString().equals("undefined")) {
			return "";
		} else {
			return obj.toString();
		}
	}

}
