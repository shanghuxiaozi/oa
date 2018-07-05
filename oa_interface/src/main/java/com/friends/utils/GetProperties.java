package com.friends.utils;

import java.io.IOException;
import java.util.Properties;

/**
 * 获取配置文件信息
 * 
 * @author pengyl
 */
public class GetProperties {
	public static String getPropertiesValue(String key) {
		Properties prop = new Properties();
		try {
			prop.load(GetProperties.class.getClassLoader().getResourceAsStream("application.properties"));
			return prop.getProperty(key);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
