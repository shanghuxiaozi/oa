package com.friends.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StringUtil {
	private static final char SEPARATOR = '_';

	// 转成下划线
	public static String toUnderlineName(String s) {
		if (s == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		boolean upperCase = false;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			boolean nextUpperCase = true;
			if (i < (s.length() - 1)) {
				nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
			}
			if ((i >= 0) && Character.isUpperCase(c)) {
				if (!upperCase || !nextUpperCase) {
					if (i > 0)
						sb.append(SEPARATOR);
				}
				upperCase = true;
			} else {
				upperCase = false;
			}
			sb.append(Character.toLowerCase(c));
		}
		return sb.toString();
	}

	// 转成驼峰法
	public static String toCamelCase(String s) {
		if (s == null) {
			return null;
		}
		s = s.toLowerCase();
		StringBuilder sb = new StringBuilder(s.length());
		boolean upperCase = false;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			if (c == SEPARATOR) {
				upperCase = true;
			} else if (upperCase) {
				sb.append(Character.toUpperCase(c));
				upperCase = false;
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	//
	public static String toCapitalizeCamelCase(String s) {
		if (s == null) {
			return null;
		}
		s = toCamelCase(s);
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}

	public static String firstUperCase(String str) {
		char[] ch = str.toCharArray();
		if (ch[0] >= 'a' && ch[0] <= 'z') {
			ch[0] = (char) (ch[0] - 32);
		}

		return new String(ch);
	}

//	public static String sqlTypeToJavaType(String sqlType) {
//		if (sqlType.equalsIgnoreCase("bit")) {
//			return "boolean";
//		} else if (sqlType.equalsIgnoreCase("tinyint")) {
//			return "byte";
//		} else if (sqlType.equalsIgnoreCase("smallint")) {
//			return "short";
//		} else if (sqlType.equalsIgnoreCase("int")) {
//			return "Integer";
//		} else if (sqlType.equalsIgnoreCase("bigint")) {
//			return "Long";
//		} else if (sqlType.equalsIgnoreCase("float")) {
//			return "Float";
//		} else if (sqlType.equalsIgnoreCase("decimal")
//				|| sqlType.equalsIgnoreCase("numeric")
//				|| sqlType.equalsIgnoreCase("real")
//				|| sqlType.equalsIgnoreCase("money")
//				|| sqlType.equalsIgnoreCase("smallmoney")
//				|| sqlType.equalsIgnoreCase("double")) {
//			return "Double";
//		} else if (sqlType.equalsIgnoreCase("varchar")
//				|| sqlType.equalsIgnoreCase("char")
//				|| sqlType.equalsIgnoreCase("nvarchar")
//				|| sqlType.equalsIgnoreCase("nchar")
//				|| sqlType.equalsIgnoreCase("text")) {
//			return "String";
//		} else if (sqlType.equalsIgnoreCase("datetime")) {
//			return "Date";
//		} else if (sqlType.equalsIgnoreCase("image")) {
//			return "Blod";
//		}
//
//		return null;
//	}

	public static Map<String, Object> tranferKeyToCamel(Map<String, Object> map, Set<String> ignoreKeys) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		Set<Map.Entry<String, Object>> entries = map.entrySet();
		for (Map.Entry<String, Object> entry : entries) {
			String key = entry.getKey();
			Object value = entry.getValue();
			
			if (ignoreKeys!=null&&ignoreKeys.contains(key)) {
				resultMap.put(key, value);
				continue;
			}
			String newkey = toCamelCase(key);
			if ((value instanceof List)) {
				List newList = new ArrayList();
				List valList = (List) value;
				for (Object val : valList) {
					Map<String, Object> subResultMap = new HashMap<String, Object>();
					subResultMap=tranferKeyToCamel((Map) val, ignoreKeys);
					newList.add(subResultMap);
				}
				resultMap.put(newkey, newList);
			} else if (value instanceof Map) {
				Map<String, Object> subResultMap = new HashMap<String, Object>();
				subResultMap=tranferKeyToCamel((Map<String, Object>)value, ignoreKeys);
				resultMap.put(newkey, subResultMap);
			} else {
				resultMap.put(newkey, value);
			}
		}
		return resultMap;
	}
	
	public static List<Map<String, Object>> tranferMapsKeyToCamel(List<Map<String, Object>> maps, Set<String> ignoreKeys){
		for (int i=0;i<maps.size();i++) {
			Map<String, Object> newMap = tranferKeyToCamel(maps.get(i),null);
			maps.set(i, newMap);
		}
		return maps;
	}
	

}
