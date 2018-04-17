package com.support.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtil {

    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static final String API_DATE_FORMAT = "dd/MM/yyyy";
    public static final String DATE_FORMAT_ADD_ON = "EEE, dd MMM yyyy";

    public static String getUTCDateTimeAsString(Date date) {
        final SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf.format(date);
    }

    public static String getCurrentDate(String format) {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());
        return formatter.format(date);
    }

    public static String getCurrentDate() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        return formatter.format(date);
    }

    public static String getCurrentDate1() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        return formatter.format(date);
    }

    public static Date stringDateToDate(String strDate) {
        return stringToDate(strDate, DATE_FORMAT);

    }

    public static Date stringToDate(String strDate, String format) {
        Date dateToReturn = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.US);

        try {
            dateToReturn = dateFormat.parse(strDate);
        } catch (ParseException e) {
            AppLog.log(false, "DateUtil: " + "stringToDate: ", e);
        }

        return dateToReturn;
    }

    public static String getFormatedDate(Date journeyDate, String format) {
        final SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
//        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf.format(journeyDate);
    }

    public static String getFormatedDate(Date journeyDate) {
        return getFormatedDate(journeyDate, "dd/MM/yyyy");
    }

    public static boolean isDateAfter(String expireDate) {
        Date expDate = stringToDate(expireDate, API_DATE_FORMAT);

        Date date = new Date();

        return expDate.compareTo(date) > 0;

    }

    public static boolean isDateBefore(String expireDate) {
        Date expDate = stringToDate(expireDate, API_DATE_FORMAT);

        Date date = new Date();

        return expDate.compareTo(date) < 0;

    }
}
