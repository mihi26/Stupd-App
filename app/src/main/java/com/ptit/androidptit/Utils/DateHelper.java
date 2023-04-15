package com.ptit.androidptit.Utils;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class DateHelper {
    public static Date parseDateFromString(String dateStr, String dateFormat) {
        try {
            return new SimpleDateFormat(dateFormat, Locale.US).parse(dateStr);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date parseDateFromString(String dateStr) {
        return parseDateFromString(dateStr, DateTimeFormatter.ISO_INSTANT.toString());
    }

    public static String formatDate(Date date, String dateFormat) {
        try {
            return new SimpleDateFormat(dateFormat, Locale.US).format(date);
        } catch (Exception e) {
            return null;
        }
    }

    public static String formatDate(Date date) {
        return formatDate(date, DateTimeFormatter.ISO_INSTANT.toString());
    }

    public static String formatDate(String dateString, String dateFormat, String targetFormat) {
        return formatDate(parseDateFromString(dateString, dateFormat), targetFormat);
    }

    public static String formatDate(String dateString, String targetFormat) {
        return formatDate(parseDateFromString(dateString, DateTimeFormatter.ISO_INSTANT.toString()), targetFormat);
    }
}
