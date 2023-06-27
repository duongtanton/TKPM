package com.tkpm.studentsmanagement.util;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimestampUtil {
    private static String format = "MM/dd/yyyy";

    public static Timestamp convertStringToTimestamp(String date) {
        if(date == null || date.isEmpty()) {
            return null;
        }
        DateTimeFormatter formatDateTime = DateTimeFormatter.ofPattern(format);
        LocalDate localDate = LocalDate.parse(date,formatDateTime);
        LocalDateTime localDateTime = localDate.atStartOfDay();
        return Timestamp.valueOf(localDateTime);
    }
}
