package com.friends.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarUtils {
	public static Date getNowDate() {
		return getDate(new Date());
	}

	public static Date getDate(Date date) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH) + 1;
		int day = now.get(Calendar.DAY_OF_MONTH);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date result = null;
		try {
			result = sdf.parse(year + "-" + month + "-" + day);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}
}
