package com.friends.utils;

public class City3Code {
	
	// 城市三字码
	public static String[] city3Codes = { "KMG", "CTU", "CKG", "CSX", "CZX", "DLC", "FOC", "KWE", "KOW", "HFE", "HRB", "HAK", "HGH", "HYN", "TNA", "LJG", "LZH", "LUM", "MIG", "KHN", "NKG", "NNG", "TAO", "TCZ", "SZX", "PVG", "SJW", "TSN", "TYN", "WUH", "XIY", "JHG", "XMN", "YCU", "YIW", "CGO", "CAN", "INC", "YTY", "NTG", "WUS", "ZUH", "LNJ", "CGQ", "SYX", "SYM", "PEK", "DAX", "SHE", "WNZ", "HUZ", "BSD", "LFQ" };
	// 城市名
	public static String[] citys = { "昆明", "成都", "重庆", "长沙", "常州", "大连", "福州", "贵阳", "赣州", "合肥", "哈尔滨", "海口", "杭州", "台州", "济南", "丽江", "柳州", "芒市", "绵阳", "南昌", "南京", "南宁", "青岛", "腾冲", "深圳", "上海浦东", "石家庄", "天津", "太原", "武汉", "西安", "西双版纳", "厦门", "运城", "义乌", "郑州", "广州", "银川", "扬州", "南通", "武夷山", "珠海", "临沧", "长春", "三亚", "普洱", "北京", "达县", "沈阳", "温州", "惠州", "保山", "临汾" };
	// 机场名
	public static String[] airports = { "长水国际机场", "双流机场", "江北机场", "黄花机场", "奔牛机场", "周水子机场", "长乐机场", "龙洞堡机场", "黄金机场", "骆岗机场", "太平机场", "美兰机场", "萧山机场", "路桥机场", "遥墙机场", "三义机场", "白莲机场", "芒市机场", "绵阳机场", "昌北机场", "禄口机场", "吴圩机场", "流亭机场", "驼峰机场", "宝安机场", "浦东国际机场", "正定机场", "滨海机场", "武宿机场", "天河机场", "咸阳机场", "嘎洒机场", "高崎机场", "关公机场", "义乌机场", "新郑机场", "白云机场", "河东机场", "扬州泰州机场", "兴东机场", "武夷山机场", "金湾机场", "临沧机场", "龙嘉机场", "凤凰机场", "普洱机场", "首都国际机场", "达州河市机场", "桃仙国际机场", "龙湾国际机场", "平潭机场", "云瑞机场", "乔李机场" };

	public static String[] getCityCnNameAndAirportCnNameByCity3Code(String city3Code) {
		for (int i = 0; i < city3Codes.length; i++) {
			if (city3Code.equals(city3Codes[i])) {
				return new String[] { citys[i], airports[i] };
			}
		}
		return null;
	}

	public static String CityCnNameToCity3Code(String cityCnName) {
		for (int i = 0; i < citys.length; i++) {
			if (cityCnName.equals(citys[i])) {
				return city3Codes[i];
			}
		}
		return null;
	}
}
