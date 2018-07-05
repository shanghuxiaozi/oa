package com.friends.utils;

import org.apache.commons.codec.binary.Base64;

public class Base64Code {

	public static String base64Encode(String str) {
		byte[] encodeBytes = Base64.encodeBase64(str.getBytes());
		return new String(encodeBytes);
	}
	
	public static String base64Decode(String str) {
		byte[] dencodeBytes = Base64.decodeBase64(str.getBytes());
		return new String(dencodeBytes);
	}
}
