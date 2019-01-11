package com.springcloud.example.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/***
 *  @Author dengwei
 *  @Description: 设备类型
 *  @Date 2019/1/10 15:44
 */
@Getter
@AllArgsConstructor
public enum ScreenTypeEunm {
    BUSINESS_IN_SCREEN(1,"商务梯内屏"),BUSINESS_OUT_SCREEN(2,"商务梯外屏"),BUSINESS_IN_FRAME(3,"商务梯内框"),
    RESIDENCE_IN_SCREEN(4,"住宅梯内屏"),RESIDENCE_OUT_SCREEN(5,"住宅梯外屏"),RESIDENCE_IN_FRAME(6,"住宅梯内框");
    private Integer code;
    private String name;
}
