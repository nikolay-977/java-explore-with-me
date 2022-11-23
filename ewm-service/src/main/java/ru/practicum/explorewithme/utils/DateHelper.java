package ru.practicum.explorewithme.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class DateHelper {
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);

    public static LocalDateTime parseDateTime(String dateTime) {
        return LocalDateTime.parse(dateTime, FORMATTER);
    }

}
