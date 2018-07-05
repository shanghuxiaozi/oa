package com.friends.utils;

import java.util.Date;

public class SegmentUtil {
	// 获取某时间所属时间段
	//0-6:00,6:01-10:00,10:01-14:00,14:01-18:00,18:01-22:00,22:01-23:59
	@SuppressWarnings("deprecation")
	public static String getTimeSegmentIn(Date time) {
		try {
//			int span = 3;
			int hours = time.getHours();
			if (hours>0 && hours<=6) {
				return "0-6:00点";
			}else if(hours>6 && hours<=10){
				return "6:01-10:00";
			}else if(hours>10 && hours<=14){
				return "10:01-14:00";
			}else if(hours>14 && hours<=18){
				return "14:01-18:00";
			}else if(hours>18 && hours<=22){
				return "18:01-22:00";
			}else if(hours>22 && hours<=24){
				return "22:01-24:00";
			}else {
				return "未知";
			}
//			return String.valueOf((hours / span) * span) + "-" + String.valueOf((hours / span + 1) * span) + "点";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// 获取所属年龄段
	// 0-12,13-24,25-40,41-60,61以上
	public static String getAgeSegmentIn(Integer age) {
		try {
			if (age==null) {
				return null;
			}
//			int span = 10;
			if (age>0 && age<=12) {
				return "0-12岁";
			}else if(age>12 && age<=24){
				return "13-24岁";
			}else if(age>24 && age<=40){
				return "25-40岁";
			}else if(age>41 && age<=60){
				return "41-60岁";
			}else if(age>60 && age<=120){
				return "61岁以上";
			}else {
				return "未知";
			}
//			return String.valueOf((age / span) * span) + "-" + String.valueOf((age / span + 1) * span) + "岁";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// 获取公里数所属段
	// 0-35,36-50,51-80,81以上（饼图）
	public static String getKmSegmentIn(Double kmDouble) {
		try {
			Integer km = kmDouble.intValue();
			if (km>0 && km<=36) {
				return "0-35公里";
			}else if(km>36 && km<=50){
				return "36-50公里";
			}else if(km>50 && km<=80){
				return "51-80公里";
			}else if(km>80){
				return "81公里以上";
			}else {
				return "未知";
			}
//			int span = 20;
//			return String.valueOf((km / span) * span) + "-" + String.valueOf((km / span + 1) * span) + "公里";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// 获取支付金额所属段
	public static String getPayAmountSegmentIn(Double payAmountDouble) {
		Integer payAmount = payAmountDouble.intValue();
		try {
			int span = 10;
			Integer amount=0;
			if ((payAmount / span)>5) {
				return "51元以上";
			}else{
				return String.valueOf((payAmount / span) * span) + "-" + String.valueOf((payAmount / span + 1) * span)
						+ "元";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
