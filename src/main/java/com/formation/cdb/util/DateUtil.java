package com.formation.cdb.util;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class DateUtil {
    /**
     * convert a string to a java.time.LocalDate.
     * @param dateString the input string (dd-MM-yyyy).
     * @return a LocalDate.
     * @throws DateTimeParseException Exception catch by Control class.
     */
    public static LocalDate stringToDate(String dateString) throws DateTimeParseException {
        DateTimeFormatter formatter;
        LocalDate date;

        formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        date = LocalDate.parse(dateString, formatter);
        return date;
    }
    public static LocalDate stringToDateDashSeparatedDDMMYYYY(String dateString) throws DateTimeParseException {
        DateTimeFormatter formatter;
        LocalDate date;

        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        date = LocalDate.parse(dateString, formatter);
        return date;
    }
    /**
     * Convert a LocalDate to a Timestamp used to insert/update/read sql Objects.
     * @param d The LocalDate.
     * @return a Timestamp.
     */
    public static Timestamp dateToTimestamp(LocalDate d) {
        return Timestamp.valueOf(d.atStartOfDay());
    }
}
