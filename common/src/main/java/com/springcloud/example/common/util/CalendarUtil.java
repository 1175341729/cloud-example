package com.springcloud.example.common.util;


import lombok.extern.slf4j.Slf4j;

import java.util.Calendar;

@Slf4j
public class CalendarUtil {
    /**
     * 获取指定周次礼拜一时间戳
     * @param nowTime 指定时间
     * @param preWeek 周次
     * @return 响应
     */
    public static long getWeekMonday(long nowTime,int preWeek){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(nowTime * 1000);

        int week = calendar.get(Calendar.DAY_OF_WEEK) == 1 ? 7 : calendar.get(Calendar.DAY_OF_WEEK) - 1;
        calendar.add(Calendar.DATE,1 - week);
        calendar.add(Calendar.WEEK_OF_YEAR,preWeek);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTimeInMillis() / 1000;
    }

    /**
     * 获取指定周次礼拜一时间戳
     * @param dataTime 指定时间
     * @return 响应
     */
    public static Calendar getYearMonthByDataTime(long dataTime){
        // 获取指定时间礼拜一所在月份
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dataTime * 1000);
        int week = calendar.get(Calendar.DAY_OF_WEEK) == 1 ? 7 : calendar.get(Calendar.DAY_OF_WEEK) - 1;
        calendar.add(Calendar.DATE,1 - week);
        return calendar;
    }

    /**
     * 根据数据日期获取开始时间
     * @param dataTime
     * @return
     */
    public static long getDayStart(long dataTime){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dataTime * 1000);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTimeInMillis() / 1000;
    }

    /**
     * 根据指定时间 获取指定天数之前之后的日期
     * @param dataTime
     * @param addDay
     * @return
     */
    public static long getAddDay(long dataTime,int addDay){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dataTime * 1000);
        calendar.add(Calendar.DATE,addDay);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTimeInMillis() / 1000;
    }

    public static void main(String[] args) {
//        List<WeekMonth> weekByMonth = getWeekByMonth(2018, 4);
//        long weekMonday = getWeekMonday(1525311713, 1);
//        System.out.println(weekMonday);
//        long dayStart = getDayStart(1524885766);
//        long dayStart = getAddDay(1524885766,-2);
//        log.info(dayStart + "");

        Calendar yearMonth = getYearMonthByDataTime(1486051200);
        System.out.println(yearMonth.get(Calendar.YEAR) + "===" + (yearMonth.get(Calendar.MONTH) + 1));
    }
}
