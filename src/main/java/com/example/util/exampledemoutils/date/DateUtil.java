package com.example.util.exampledemoutils.date;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;


public final class DateUtil {

    private final static DateTimeFormatter formatter =
            new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
                    .appendOffsetId()
                    .toFormatter();
    private final static DateTimeFormatter formatterNoMillis =
            new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd'T'HH:mm:ss")
                    .appendOffsetId()
                    .toFormatter();
    public static final ZoneId TIME_ZONE = ZoneId.of("Asia/Kuala_Lumpur");

    /**
     * Check if the given String is ISO 8601 Date Format
     *
     * @param dateStr
     * @return
     */
    public static Boolean isISO8601(String dateStr) {
        return parseISO8601(dateStr) != null;
    }

    public static Boolean isISO8601WithoutMillis(String dateStr) {
        return parseISO8601WithoutMillis(dateStr) != null;
    }

    /**
     * Parse the given String from ISO 8601 format to Date object
     *
     * @param dateStr
     * @return
     */
    public static Date parseISO8601(String dateStr) {
        if (dateStr == null)
            return null;

        try {
            OffsetDateTime offsetDateTime = OffsetDateTime.parse(dateStr);

            if (offsetDateTime != null) {
                return Date.from(offsetDateTime.toInstant());
            }
        } catch (java.time.format.DateTimeParseException ex) {
            return null;
        }

        return null;
    }

    public static Instant parseISO8601WithoutMillis(String dateStr) {
        if (dateStr == null)
            return null;
        return Instant.from(formatterNoMillis.parse(dateStr));
    }


    /**
     * Convert Date to LocalDateTime
     *
     * @param dateToConvert date to convert
     * @return
     */
    public static LocalDateTime convertToLocalDateTime(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    /**
     * Convert Date to Local Date
     *
     * @param dateToConvert date to convert
     * @return
     */
    public static LocalDate convertToLocalDate(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    /**
     * Format the Date object to ISO 8601 Date String
     *
     * @param date
     * @return
     */
    public static String formatISO8601(Date date) {
        if (date == null)
            return null;

        return OffsetDateTime.ofInstant(date.toInstant(), TIME_ZONE)
                .toZonedDateTime()
                .format(formatter);
    }

    public static String formatISO8601(Instant instant) {
        if (instant == null)
            return null;

        return formatter.format(instant.atZone(TIME_ZONE));
    }

    public static String formatISO8601WithoutMillis(Instant instant) {
        return formatterNoMillis.format(instant.atZone(TIME_ZONE));
    }

    /**
     * Get current Date in ISO 8601 Date String
     *
     * @return
     */
    public static String nowISO8601() {
        return OffsetDateTime.ofInstant(Instant.now(), TIME_ZONE)
                .toZonedDateTime()
                .format(formatter);
    }

    public static String nowISO8601NoMillis() {
        return OffsetDateTime.ofInstant(Instant.now(), TIME_ZONE)
                .toZonedDateTime()
                .format(formatterNoMillis);
    }

    /**
     * Get only date (yyyy-MM-dd) from ISO 8601 Date String
     *
     * @param dateStr
     * @return
     */
    public static String extractDateFromISO8601(String dateStr) {
        OffsetDateTime offsetDateTime = OffsetDateTime.parse(dateStr);
        if (offsetDateTime != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return simpleDateFormat.format(Date.from(offsetDateTime.toInstant()));
        }

        return null;
    }

    /**
     * Get current Date with Simple Date Format
     *
     * @return
     */
    public static String now() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return sdf.format(new Date());
    }

    /**
     * Get the String format with Pattern given
     *
     * @param pattern the date format pattern, e.g. yyyyMMddHHmm
     * @param date    the date object
     * @return the date in String with pattern format or null if failed to format
     */
    public static String getFormatWithPattern(String pattern, Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * @param source
     * @param destination
     * @return
     */
    public static Duration compareDateTime(Date source, Date destination) {
        LocalDateTime sourceTime = DateUtil.convertToLocalDateTime(source);
        LocalDateTime destinationTime = DateUtil.convertToLocalDateTime(destination);
        return Duration.between(sourceTime, destinationTime);
    }

    /**
     * @param source
     * @param destination
     * @return
     */
    public static Period compareDate(Date source, Date destination) {
        LocalDate sourceDate = DateUtil.convertToLocalDate(source);
        LocalDate destinationDate = DateUtil.convertToLocalDate(destination);
        return Period.between(sourceDate, destinationDate);
    }

    public static Instant toInstant(Date date) {
        if (date == null) {
            return null;
        } else {
            return date.toInstant();
        }
    }

}
