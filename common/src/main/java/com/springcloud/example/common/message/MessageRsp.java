package com.springcloud.example.common.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageRsp<T> {
    private Integer code;
    private String message;
    //  @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private T data;

}
