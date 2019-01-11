package com.springcloud.example.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/***
 *  @Author dengwei
 *  @Description: TODO
 *  @Date 2019/1/10 15:50
 */
@Getter
@AllArgsConstructor
public enum TaskTypeEnum {
    DEVELOP_SIGN((byte)1,"开发任务"),SELL_SIGN((byte)2,"可售任务"),INSTALL_CHARGE((byte)3,"安装任务");
    private Byte code;
    private String name;
}
