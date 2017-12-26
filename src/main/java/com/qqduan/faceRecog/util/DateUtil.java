package com.qqduan.faceRecog.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	static String datetimeFormat = "yyyy-MM-dd";

	public static Date getDateFromString(String s) {
		Date returnDate = new Date();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(datetimeFormat);
			returnDate = sdf.parse(s);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return returnDate;
	}

	public static int compareDate(String strDate1, String strDate2) {
		int returnVal = 0;
		try {
			Date date1 = getDateFromString(strDate1 + " 00:00:00");
			Date date2 = getDateFromString(strDate2 + " 00:00:00");
			returnVal = (int) ((date2.getTime() - date1.getTime()) / (1000 * 60 * 60 * 24));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnVal;
	}
	
}
