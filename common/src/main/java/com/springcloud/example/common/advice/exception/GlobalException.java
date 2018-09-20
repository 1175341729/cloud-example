package com.springcloud.example.common.advice.exception;

import com.springcloud.example.common.enums.CommonEnum;
import lombok.Data;

// 自定义全局异常
@Data
public class GlobalException extends RuntimeException {
    private Integer code = CommonEnum.Message.ERROR.getCode();

    public GlobalException(String message) {
        super(message);
    }

    public GlobalException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
