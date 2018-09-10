package com.springcloud.example.common.message;

import lombok.Data;

@Data
public class MessageRsp<T> {
    private Integer code;
    private String message;
    //  @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private T data;

}
