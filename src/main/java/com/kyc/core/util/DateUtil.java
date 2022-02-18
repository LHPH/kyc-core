package com.kyc.core.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static com.kyc.core.constants.GeneralConstants.FORMAT_DATE_YEAR_MONTH_DAY_TIME_WITH_HYPHEN_ENGLISH;
import static com.kyc.core.constants.GeneralConstants.FORMAT_DATE_YEAR_MONTH_DAY_WITH_HYPHEN_ENGLISH;


public final class DateUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(DateUtil.class);

    public static Date stringToDate(String date,String format) {

        try {
            SimpleDateFormat sd = new SimpleDateFormat(format);
            return sd.parse(date);
        }
        catch(ParseException ex) {
            LOGGER.error(" ",ex);
            return null;
        }
    }

    public static Date stringToDate(String date) {

       return stringToDate(date,FORMAT_DATE_YEAR_MONTH_DAY_WITH_HYPHEN_ENGLISH);
    }

    public static LocalDate stringToLocalDate(String date) {

       return stringToLocalDate(date,FORMAT_DATE_YEAR_MONTH_DAY_WITH_HYPHEN_ENGLISH);
    }

    public static LocalDate stringToLocalDate(String date,String format) {

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            return LocalDate.parse(date,formatter);
        }
        catch(DateTimeParseException ex) {
            LOGGER.error(" ",ex);
            return null;
        }
    }

    public static LocalDateTime stringToLocalDateTime(String date) {

       return stringToLocalDateTime(date,FORMAT_DATE_YEAR_MONTH_DAY_TIME_WITH_HYPHEN_ENGLISH);
    }

    public static LocalDateTime stringToLocalDateTime(String date,String format) {

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            return LocalDateTime.parse(date,formatter);
        }
        catch(DateTimeParseException ex) {
            LOGGER.error(" ",ex);
            return null;
        }
    }

    public static String dateToString(Date date,String format) {

        SimpleDateFormat sd = new SimpleDateFormat(format);
        return sd.format(date);

    }

    public static String dateToString(Date date) {

        return dateToString(date,FORMAT_DATE_YEAR_MONTH_DAY_WITH_HYPHEN_ENGLISH);
    }

    public static String dateLocalToString(LocalDate date,String format) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return formatter.format(date);
    }

    public static String dateLocalToString(LocalDate date) {

        return dateLocalToString(date,FORMAT_DATE_YEAR_MONTH_DAY_WITH_HYPHEN_ENGLISH);
    }

    public static String dateLocalTimeToString(LocalDateTime date,String format) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return formatter.format(date);
    }

    public static String dateLocalTimeToString(LocalDateTime date) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_DATE_YEAR_MONTH_DAY_TIME_WITH_HYPHEN_ENGLISH);
        return formatter.format(date);
    }

    public static LocalDate dateToLocalDate(Date date) {

        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDateTime dateToLocalDateTime(Date date) {

        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static Date localDateToDate(LocalDate date) {
        return Date.from(date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date localDateTimeToDate(LocalDateTime date) {
        return Date.from(date.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Long getDifference(LocalDateTime first, LocalDateTime second, ChronoUnit unit){

        if(first!=null && second!=null && unit!=null){
            return unit.between(first,second);
        }
        return null;
    }

    public static boolean isValidDate(String date){

        try{
            DateTimeFormatter formatter=DateTimeFormatter.ofPattern("uuuu-MM-dd")
                    .withResolverStyle(ResolverStyle.STRICT);
            LocalDate.parse(date,formatter);
            return true;
        }
        catch(DateTimeParseException ex){
            return false;
        }
    }

    private DateUtil(){}
}
