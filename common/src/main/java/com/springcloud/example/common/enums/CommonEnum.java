package com.springcloud.example.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class CommonEnum {
    @AllArgsConstructor
    @Getter
    public enum Message {
        SUCCESS(200,"成功"),ERROR(500,"请求失败"),RUNTIMEEXCEPTION(5001,"未知异常"),AUTHEXCEPTION(4001,"认证失败"),NOT_FOUND(404001,"【请求资源未找到】"),METHOD_NOT_ALLOWD(405001,"【方法请求方式异常】");

        private Integer code;
        private String message;
    }
}
