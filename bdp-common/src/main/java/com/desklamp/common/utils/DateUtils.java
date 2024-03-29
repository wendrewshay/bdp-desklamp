package com.desklamp.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author Erin
 * @create 2019-03-23 15:37
 */
public class DateUtils {

    /**
     * Date转自定义格式时间字符串
     *
     * @param date   时间
     * @param format 自定义格式
     * @return java.lang.String
     * @author Erin
     * @date 2019-03-23 15:38
     */
    public static String formatDate(Date date, String format) throws Exception {
        if (StringUtils.isEmpty(date)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 时间戳转自定义格式时间字符串
     *
     * @param time   时间戳
     * @param format 自定义格式
     * @return java.lang.String
     * @author Erin
     * @date 2019-03-23 15:50
     */
    public static String formatTime(Long time, String format) throws Exception {
        if (StringUtils.isEmpty(time)) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return new SimpleDateFormat(format).format(calendar.getTime());
    }

    /**
     * 获取当天0点
     * @return Date
     * @author by Joney on 2019/4/9 17:28
     */
    public static Date todayFirstDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取当天23点59分59秒
     * @return Date
     * @author by Joney on 2019/4/9 17:28
     */
    public static Date todayLastDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }
}
