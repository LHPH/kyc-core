package com.kyc.core.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

@RunWith(MockitoJUnitRunner.class)
public class DateUtilTest {

    @Test
    public void stringToDate_convertStringToDate_returnDateType(){
        Date date = DateUtil.stringToDate("1990-10-10","yyyy-MM-dd");

        Assert.assertNotNull(date);
        LocalDateTime localDate = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        Assert.assertEquals(1990,localDate.getYear());
        Assert.assertEquals(10,localDate.getMonthValue());
        Assert.assertEquals(10,localDate.getDayOfMonth());

    }

    @Test
    public void stringToDate_convertBadStringToDate_returnNullByParseException(){

        Date date = DateUtil.stringToDate("1990/10/10","yyyy-MM-dd");
        Assert.assertNull(date);
    }

    @Test
    public void stringToDate_convertStringToDateWithDefaultFormat_returnDateType(){

        Date date = DateUtil.stringToDate("1990-10-10");

        Assert.assertNotNull(date);
        LocalDateTime localDate = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        Assert.assertEquals(1990,localDate.getYear());
        Assert.assertEquals(10,localDate.getMonthValue());
        Assert.assertEquals(10,localDate.getDayOfMonth());

    }

    @Test
    public void stringToLocalDate_convertStringToLocalDate_returnLocalDate(){

        LocalDate date = DateUtil.stringToLocalDate("1990-10-10","yyyy-MM-dd");
        Assert.assertNotNull(date);
        Assert.assertEquals(1990,date.getYear());
        Assert.assertEquals(10,date.getMonthValue());
        Assert.assertEquals(10,date.getDayOfMonth());
    }

    @Test
    public void stringToLocalDate_convertStringToLocalDateWithDefaultFormat_returnLocalDate(){

        LocalDate date = DateUtil.stringToLocalDate("1990-10-10");
        Assert.assertNotNull(date);
        Assert.assertEquals(1990,date.getYear());
        Assert.assertEquals(10,date.getMonthValue());
        Assert.assertEquals(10,date.getDayOfMonth());
    }

    @Test
    public void stringToLocalDate_convertBadString_returnNullByParseException(){

        LocalDate date = DateUtil.stringToLocalDate("1990-10/10");
        Assert.assertNull(date);
    }

    @Test
    public void stringToLocalDateTime_convertStringToLocalDateTime_returnLocalDateTime(){

        LocalDateTime date = DateUtil.stringToLocalDateTime("1990-10-10 20:10:10");
        Assert.assertNotNull(date);
        Assert.assertEquals(1990,date.getYear());
        Assert.assertEquals(10,date.getMonthValue());
        Assert.assertEquals(10,date.getDayOfMonth());
        Assert.assertEquals(20,date.getHour());
        Assert.assertEquals(10,date.getMinute());
        Assert.assertEquals(10,date.getSecond());

    }

    @Test
    public void stringToLocalDateTime_convertStringToLocalDateTimeWithFormat_returnLocalDateTime(){

        LocalDateTime date = DateUtil.stringToLocalDateTime("1990-10-10 20:10:10","yyyy-MM-dd HH:mm:ss");
        Assert.assertNotNull(date);
        Assert.assertEquals(1990,date.getYear());
        Assert.assertEquals(10,date.getMonthValue());
        Assert.assertEquals(10,date.getDayOfMonth());
        Assert.assertEquals(20,date.getHour());
        Assert.assertEquals(10,date.getMinute());
        Assert.assertEquals(10,date.getSecond());

    }

    @Test
    public void stringToLocalDateTime_convertStringToLocalDateTime_returnNullByParseException(){

        LocalDateTime date = DateUtil.stringToLocalDateTime("1990-10-10 20:10:1");
        Assert.assertNull(date);
    }

    @Test
    public void dateToString_convertDateToString_returnStringType(){

        Calendar cal = Calendar.getInstance();
        cal.set(1990,Calendar.OCTOBER,10);

        String result = DateUtil.dateToString(cal.getTime());
        Assert.assertEquals("1990-10-10",result);
    }

    @Test
    public void dateToString_convertDateToStringWithFormat_returnStringType(){

        Calendar cal = Calendar.getInstance();
        cal.set(1990,Calendar.OCTOBER,10);

        String result = DateUtil.dateToString(cal.getTime(),"dd-MM-yyyy");
        Assert.assertEquals("10-10-1990",result);
    }

    @Test
    public void dateLocalToString_convertDateToString_returnStringType(){

        LocalDate date = LocalDate.of(1990, Month.OCTOBER,10);
        String result = DateUtil.dateLocalToString(date);
        Assert.assertEquals("1990-10-10",result);
    }

    @Test
    public void dateLocalToString_convertDateToStringWithFormat_returnStringType(){

        LocalDate date = LocalDate.of(1990, Month.OCTOBER,10);
        String result = DateUtil.dateLocalToString(date,"yyyy-MM-dd");
        Assert.assertEquals("1990-10-10",result);
    }

    @Test
    public void dateLocalTimeToString_convertDateToString_returnStringType(){

        LocalDateTime date = LocalDateTime.of(1990, Month.OCTOBER,10,14,10,10);
        String result = DateUtil.dateLocalTimeToString(date);
        Assert.assertEquals("1990-10-10 14:10:10",result);
    }

    @Test
    public void dateLocalTimeToString_convertDateToStringWithFormat_returnStringType(){

        LocalDateTime date = LocalDateTime.of(1990, Month.OCTOBER,10,14,10,10);
        String result = DateUtil.dateLocalTimeToString(date,"dd/MM/yyyy HH:mm:ss");
        Assert.assertEquals("10/10/1990 14:10:10",result);
    }

    @Test
    public void dateToLocalDate_convertDateTypes_returnLocalDateType(){

        Calendar cal = Calendar.getInstance();
        cal.set(1990,Calendar.OCTOBER,10);
        LocalDate date = DateUtil.dateToLocalDate(cal.getTime());
        Assert.assertNotNull(date);
        Assert.assertEquals(1990,date.getYear());
        Assert.assertEquals(10,date.getMonthValue());
        Assert.assertEquals(10,date.getDayOfMonth());

    }

    @Test
    public void dateToLocalDateTime_convertDateTypes_returnLocalDateTimeType(){

        Calendar cal = Calendar.getInstance();
        cal.set(1990,Calendar.OCTOBER,10,20,10,10);
        LocalDateTime date = DateUtil.dateToLocalDateTime(cal.getTime());
        Assert.assertNotNull(date);
        Assert.assertEquals(1990,date.getYear());
        Assert.assertEquals(10,date.getMonthValue());
        Assert.assertEquals(10,date.getDayOfMonth());
        Assert.assertEquals(20,date.getHour());
        Assert.assertEquals(10,date.getMinute());
        Assert.assertEquals(10,date.getSecond());

    }

    @Test
    public void localDateToDate_convertDateTypes_returnDate(){

        Date date = DateUtil.localDateToDate(LocalDate.of(1990,Month.OCTOBER,10));

        Assert.assertNotNull(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        Assert.assertEquals(1990,cal.get(Calendar.YEAR));
        Assert.assertEquals(10,cal.get(Calendar.MONTH)+1);
        Assert.assertEquals(10,cal.get(Calendar.DAY_OF_MONTH));

    }
    
    @Test
    public void localDateTimeToDate_convertDateTypes_returnDate(){

        Date date = DateUtil.localDateTimeToDate(LocalDateTime.of(1990,Month.OCTOBER,10,20,10,10));

        Assert.assertNotNull(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        Assert.assertEquals(1990,cal.get(Calendar.YEAR));
        Assert.assertEquals(10,cal.get(Calendar.MONTH)+1);
        Assert.assertEquals(10,cal.get(Calendar.DAY_OF_MONTH));
        Assert.assertEquals(20,cal.get(Calendar.HOUR_OF_DAY));
        Assert.assertEquals(10,cal.get(Calendar.MINUTE));
        Assert.assertEquals(10,cal.get(Calendar.SECOND));

    }

    @Test
    public void getDifference_processDifferenceBetweenValidDates_returnDifference(){

        LocalDateTime first = LocalDateTime.of(2020,10,10,0,0,0);
        LocalDateTime second = LocalDateTime.of(2020,10,11,0,0,0);

        long difference = DateUtil.getDifference(first,second, ChronoUnit.DAYS);
        Assert.assertEquals(1L,difference);
    }

    @Test
    public void getDifference_invalidFirstDate_returnNull(){

        LocalDateTime first = null;
        LocalDateTime second = LocalDateTime.of(2020,10,11,0,0,0);

        Assert.assertNull(DateUtil.getDifference(first,second, ChronoUnit.DAYS));
    }

    @Test
    public void getDifference_invalidSecondDate_returnNull(){

        LocalDateTime first = LocalDateTime.of(2020,10,11,0,0,0);
        LocalDateTime second = null;

        Assert.assertNull(DateUtil.getDifference(first,second, ChronoUnit.DAYS));
    }

    @Test
    public void getDifference_invalidUnit_returnDifference(){

        LocalDateTime first = LocalDateTime.of(2020,10,10,0,0,0);
        LocalDateTime second = LocalDateTime.of(2020,10,11,0,0,0);

        Assert.assertNull(DateUtil.getDifference(first,second,null));
    }

    @Test
    public void isValidDate_validateCorrectDate_returnTrue(){

        Assert.assertTrue(DateUtil.isValidDate("1991-10-20"));
    }

    @Test
    public void isValidDate_validateIncorrectDate_returnTrue(){

        Assert.assertFalse(DateUtil.isValidDate("1991-12-40"));
    }


}
