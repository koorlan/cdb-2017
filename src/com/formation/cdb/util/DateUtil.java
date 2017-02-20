package com.formation.cdb.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	public static Date StringToDate(String dateString){
		SimpleDateFormat formatter;
		Date date;
		try {
			formatter = new SimpleDateFormat("dd-MM-yyyy");
			date = formatter.parse(dateString);
			return date;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
}
