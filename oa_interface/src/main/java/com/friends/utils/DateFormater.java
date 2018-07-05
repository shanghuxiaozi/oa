package com.friends.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DateFormater {

	private final static Log log = LogFactory.getLog(DateFormater.class);

	/**
	 * 获取指定的日期格式
	 * 
	 * @param date
	 * @return
	 */
	public static String getDateFormatByDate(Date date) {
		String DateFormat = null;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			DateFormat = formatter.format(date);
		} catch (Exception e) {
			// TODO: handle exception
			log.info("转化日期格式异常：" + e);
		}
		return DateFormat;
	}

}
