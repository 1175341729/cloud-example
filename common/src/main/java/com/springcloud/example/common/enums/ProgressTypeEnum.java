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
public enum ProgressTypeEnum {
    NEW_SIGN((byte)1,"新签"),CONTINUE_SIGN((byte)2,"续签"),CONTINUE_CHARGE((byte)3,"续费");
    private Byte code;
    private String name;
}
