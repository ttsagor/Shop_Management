package com.rezaandreza.shop.System.DateTime;

import android.text.format.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeCalculation {
    public static boolean checkValidityDate(String start_date,String end_date)
    {
        return checkValidityDateTime(start_date + " 00:00:00",end_date +" 23:59:59");
    }
    public static boolean checkValidityDateTime(String start_date,String end_date)
    {
        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
        final String currentDate = DateFormat.format("yyyy-MM-dd  HH:mm:ss", currentTimestamp).toString();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date sDate = sdf.parse(start_date);
            Date cDate = sdf.parse(currentDate);
            Date eDate = sdf.parse(end_date);
            boolean c1 = (sDate.before(cDate));
            boolean c2 = (cDate.before(eDate));
            return  c1 && c2;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return false;
    }
    public static String getCurrentDateTime()
    {
        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
        return DateFormat.format("yyyy-MM-dd HH:mm:ss", currentTimestamp).toString();
    }
    public static String getCurrentDate()
    {
        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
        return DateFormat.format("yyyy-MM-dd", currentTimestamp).toString();
    }
    public static String getDateTime(Calendar calendar)
    {
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
        return DateFormat.format("yyyy-MM-dd HH:mm:ss", currentTimestamp).toString();
    }
    public static String getDate(Calendar calendar)
    {
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
        return DateFormat.format("yyyy-MM-dd", currentTimestamp).toString();
    }
    public static String getCurrentTimeStamp()
    {
        Long tsLong = System.currentTimeMillis()/1000;
        return tsLong.toString();
    }
    public static Calendar getCalender(String str)
    {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(str);
            return sdf.getCalendar();
        }catch (Exception e)
        {
            return null;
        }
    }
    public static String getDate(String date)
    {
        try {
            return date.split(" ")[0];
        }catch (Exception e)
        {
            return date;
        }
    }
}
