package com.wyfx.sf.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by liu on 2017/9/14.
 */
public class DateUtils {
    /**
     * 将时间戳转换为时间
     */
    public static String stampToDate(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    public static void main(String[] args) {
        String s = stampToDate("1262312474702");
        System.err.println(s);
    }


    /**
     * 字符串转时间戳
     *
     * @param str
     * @return
     */
    public static Long stringToTime(String str) {
        if (str == null || "".equals(str))
            return null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = dateFormat.parse(str);

        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }
}
