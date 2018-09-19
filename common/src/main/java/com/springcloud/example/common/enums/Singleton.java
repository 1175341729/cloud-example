package com.springcloud.example.common.enums;

import com.springcloud.example.common.util.CalendarUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/***
 *  @Author dengwei
 *  @Description: 枚举单利
 *  @Date 2018/9/19 17:50
 */
@AllArgsConstructor
@Getter
@Slf4j
public enum Singleton {
    INSTANCE;
    private CalendarUtil calendarUtil;
    Singleton(){
        calendarUtil = new CalendarUtil();
    }
    public CalendarUtil getInstance(){
        return calendarUtil;
    }
}
