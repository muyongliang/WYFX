package com.wyfx.ft.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by liu on 2017/7/25.
 */
public class DateUtils {


    public static String dateToString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = dateFormat.format(date);
        return format;
    }

    /**
     * 收集起始时间到结束时间之间所有的时间并以字符串集合方式返回
     *
     * @param timeStart
     * @param timeEnd
     * @return
     */
    public static List<String> collectLocalDates(String timeStart, String timeEnd) {
        return collectLocalDates(LocalDate.parse(timeStart), LocalDate.parse(timeEnd));
    }


    /**
     * 收集起始时间到结束时间之间所有的时间并以字符串集合方式返回
     *
     * @param start
     * @param end
     * @return
     */
    public static List<String> collectLocalDates(LocalDate start, LocalDate end) {
        // 用起始时间作为流的源头，按照每次加一天的方式创建一个无限流
        return Stream.iterate(start, localDate -> localDate.plusDays(1))
                // 截断无限流，长度为起始时间和结束时间的差+1个
                .limit(ChronoUnit.DAYS.between(start, end) + 1)
                // 由于最后要的是字符串，所以map转换一下
                .map(LocalDate::toString)
                // 把流收集为List
                .collect(Collectors.toList());
    }
}
