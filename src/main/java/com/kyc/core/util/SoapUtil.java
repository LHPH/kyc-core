package com.kyc.core.util;

import javax.xml.bind.DatatypeConverter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public final class SoapUtil {

    public static Date parseDate(String v) {
        return DatatypeConverter.parseDate(v).getTime();
    }

    public static String printDate(Date v)  {
        Calendar cal = new GregorianCalendar();
        cal.setTime(v);
        return DatatypeConverter.printDate(cal);
    }

    private SoapUtil(){}
}
