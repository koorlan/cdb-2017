package com.formation.cdb.util;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class DateUtil {
	public static LocalDate StringToDate(String dateString){
		DateTimeFormatter formatter;
		LocalDate date;

		formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		date = LocalDateTime.parse(dateString, formatter).toLocalDate();
		return date;
	}
	
	public static Timestamp dateToTimestamp(LocalDate d){
		return Timestamp.valueOf(d.atStartOfDay());
	}
}
