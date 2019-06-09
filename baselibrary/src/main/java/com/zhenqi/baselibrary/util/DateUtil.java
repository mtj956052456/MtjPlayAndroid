package com.zhenqi.baselibrary.util;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 创建人:孟腾蛟
 * 时间: 2017/12/17
 * 描述: 冬防时间选择POP
 */
public class DateUtil {

    /**
     * 获取冬防可选时间
     *
     * @return
     */
    public static String getDFTime() {
        String nowYear = getNowYearDate();                         //今年
        String lastYear = String.valueOf(Integer.parseInt(nowYear) - 1);    //去年
        String beforeDate = getBeforeDate(1);
        long yesterdayYYYYMMdd = getLongDateYYYYMMdd(beforeDate);               //昨天的时间
        long nowYearYYYYMMdd = getLongDateYYYYMMdd(nowYear + "-03-31");   //今年冬防结束时间
        long lastYearYYYYMMdd = getLongDateYYYYMMdd(lastYear + "-10-01"); //去年冬防开始时间
        if (yesterdayYYYYMMdd >= lastYearYYYYMMdd && yesterdayYYYYMMdd <= nowYearYYYYMMdd) { //区间内才可以选择昨天
            return getBeforeDate(1);
        } else {
            return nowYear + "-03-31";
        }
    }

    /**
     * 获取冬防可选时间
     *
     * @param time 选择的时间
     * @return
     */
    public static boolean isDFTime(String time) {
        String nowYear = getNowYearDate();                         //今年
        String lastYear = String.valueOf(Integer.parseInt(nowYear) - 1);    //去年
        long yesterdayYYYYMMdd = getLongDateYYYYMMdd(time);               //选择的时间
        long nowYearYYYYMMdd = getLongDateYYYYMMdd(nowYear + "-03-31");   //今年冬防结束时间
        long lastYearYYYYMMdd = getLongDateYYYYMMdd(lastYear + "-10-01"); //去年冬防开始时间
        if (yesterdayYYYYMMdd >= lastYearYYYYMMdd && yesterdayYYYYMMdd <= nowYearYYYYMMdd) { //区间内才可以选择昨天
            return true;
        } else {
            return false;
        }
    }


    /**
     * 返回时间的毫秒数
     *
     * @return
     */
    public static long getLongDate(String time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return dateFormat.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 返回时间的毫秒数
     *
     * @return
     */
    public static long getLongDateYYYYMMdd(String time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getTypeTime(long time, String type) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(type);
        return dateFormat.format(time);
    }


    /**
     * 返回几分钟之前的毫秒数
     *
     * @return
     */
    public static long getLongDate(int minute) {
        Date date = new Date();
        return date.getTime() - 1000 * 60 * minute;
    }

    /**
     * 返回前day天的数据
     *
     * @return
     */
    public static String getBeforeDate(int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -day);    //得到前一天
        Date date = calendar.getTime();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(date);
    }

    /**
     * 返回前day天的数据
     *
     * @return
     */
    public static String getBeforeDate(int day, String type) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -day);    //得到前一天
        Date date = calendar.getTime();
        DateFormat df = new SimpleDateFormat(type);
        return df.format(date);
    }


    /**
     * 返回前day天的数据
     *
     * @return
     */
    public static String getBeforeDate(int day, int hour, String type) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -day);    //得到前N天
        calendar.add(Calendar.HOUR, -hour);    //得到前小时
        Date date = calendar.getTime();
        DateFormat df = new SimpleDateFormat(type);
        return df.format(date);
    }


    /**
     * 返回前hour小时的数据
     *
     * @return
     */
    public static String getBeforeHourDate( int hour, String type) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, -hour);    //得到前小时
        Date date = calendar.getTime();
        DateFormat df = new SimpleDateFormat(type);
        return df.format(date);
    }

    /**
     * 返回前 hour 小时的数据
     *
     * @return
     */
    public static String getBeforeHourDate(int hour) {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, -hour);    //得到前一小时
        Date date = calendar.getTime();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(date);
    }

    /**
     * 返回前month 月的数据
     *
     * @return
     */
    public static String getBeforeMonthDate(int month) {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -month);    //得到前一月
        Date date = calendar.getTime();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(date);
    }


    /**
     * 时间转换
     *
     * @param date
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getTimeTransformation(String date, int type) {
        if (TextUtils.isEmpty(date)) {
            return "";
        }
        SimpleDateFormat df1, df2;
        if (0 == type) {
            df1 = new SimpleDateFormat("yyyy-MM-dd");
            df2 = new SimpleDateFormat("yyyy年MM月dd日");
        } else {
            df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            df2 = new SimpleDateFormat("yyyy年MM月dd日HH时");
        }
        String result = "";
        try {
            result = df2.format(df1.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 日期格式字符串转换成时间戳
     *
     * @param format 如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static long getTimeToSimple(String date_str, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(date_str).getTime() / 1000;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 返回当前时24小时制
     */
    public static String getNow24HourDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        return sdf.format(new Date());
    }

    /**
     * 返回当前时12小时制
     */
    public static String getNowHourDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("hh");
        return sdf.format(new Date());
    }

    /**
     * 返回当前分钟
     */
    public static String getNowMinuteDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("mm");
        return sdf.format(new Date());
    }

    /**
     * 返回当前秒
     */
    public static String getNowSecondDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("ss");
        return sdf.format(new Date());
    }

    /**
     * 返回当前年
     */
    public static int getThisYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 返回当前月
     */
    public static int getThisMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 返回当前日
     */
    public static int getThisDay() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 返回当前时间格式
     * @param format
     * @return
     */
    public static String getNowDate(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date());
    }

    /**
     * 返回当前日
     */
    public static String getNowDayDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        return sdf.format(new Date());
    }

    /**
     * 返回当月
     */
    public static String getNowMonthDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        return sdf.format(new Date());
    }

    /**
     * 返回当前年
     */
    public static String getNowYearDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        return sdf.format(new Date());
    }

    /**
     * 返回当前年月日
     */
    public static String getNowDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }

    /**
     * 返回当前年月日 时分秒 时间
     */
    public static String getNowDateTime() {
        Date date = new Date();
        String nowDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        return nowDate;
    }

    /**
     * 返回当前 年月日时 时间
     */
    public static String getNowyyyyMMddHHTime() {
        return new SimpleDateFormat("yyyy-MM-dd-HH").format(new Date());
    }

    /**
     * 返回当前年月日 时分秒 时间
     */
    public static String getNowddHHmmTime() {
        return new SimpleDateFormat("MM月 dd日").format(new Date());
    }

    //获取今天
    public static String getToday(String type) {
        Date date = new Date();
        long time = date.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat(type, Locale.getDefault());
        return dateFormat.format(time);
    }

    //获取明天
    public static String getTomorrow(String type) {
        Date date = new Date();
        long time = date.getTime();
        time = time + 24 * 60 * 60 * 1000;
        SimpleDateFormat dateFormat = new SimpleDateFormat(type, Locale.getDefault());
        return dateFormat.format(time);
    }

    //获取后天
    public static String getAcquired(String type) {
        Date date = new Date();
        long time = date.getTime();
        time = time + 24 * 60 * 60 * 1000 * 2;
        SimpleDateFormat dateFormat = new SimpleDateFormat(type, Locale.getDefault());
        return dateFormat.format(time);
    }

    /**
     * 返回当前年月日 时分秒 时间
     */
    public static String getYesterdayyyyyMMddTime(int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -day);    //得到前一天
        Date date = calendar.getTime();
        DateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
        return df.format(date);
    }


    /**
     * 获取当天周几
     *
     * @param dt
     * @return
     */
    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    /**
     * 获取小时和分钟
     *
     * @return
     */
    public static String getHHmmTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("a:hh:mm");
        String time = sdf.format(date);
        String[] times = time.split(":");
        return times[0] + "-" + times[1] + ":" + times[2];
    }

    public static String gethhmmTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
        return sdf.format(date);
    }

    /**
     * 获取上午下午
     *
     * @return
     */
    public static String getAMPM() {
        Calendar cal = Calendar.getInstance();
        int bool = cal.get(Calendar.AM_PM);
        if (bool == 0) {
            return "上午";
        }
        return "下午";
    }

    /**
     * 返回当月天数
     *
     * @param year
     * @param month
     * @return
     */
    public static int getDays(int year, int month) {
        int days;
        int FebDay = 28;
        if (isLeap(year))
            FebDay = 29;
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                days = 31;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                days = 30;
                break;
            case 2:
                days = FebDay;
                break;
            default:
                days = 0;
                break;
        }
        return days;
    }

    /**
     * 判断闰年
     */
    public static boolean isLeap(int year) {
        return ((year % 100 == 0) && year % 400 == 0) || ((year % 100 != 0) && year % 4 == 0);
    }

    /**
     * 获取一年有多少天
     *
     * @return
     */
    public static int getYearDay() {
        return isLeap(getThisYear()) ? 366 : 365;
    }

    /**
     * 获取已经过了多少天
     * @return
     */
    public static int getPastDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return calendar.get(Calendar.DAY_OF_YEAR);
    }

}
