package com.springcloud.example.common.enums;

import com.springcloud.example.common.util.CalendarUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/***
 *  @Author dengwei
 *  @Description: 枚举单利
 *  枚举本身就是单利  可以直接实现相关逻辑 不用再次创建
 *  @Date 2018/9/19 17:50
 */
@AllArgsConstructor
@Getter
@Slf4j
public enum Singleton {
    INSTANCE;
    private CalendarUtil calendarUtil;
    private List<String> codes = new ArrayList<>();
    Singleton(){
        calendarUtil = new CalendarUtil();
    }
    public CalendarUtil getInstance(){
        return calendarUtil;
    }

    public void setCodes(List<String> codes) {
        this.codes = codes;
    }
}