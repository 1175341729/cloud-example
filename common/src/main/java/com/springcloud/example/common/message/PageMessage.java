package com.springcloud.example.common.message;

import lombok.Data;

@Data
public class PageMessage<T> {
    private Integer limit;
    private Integer offset;
    private Integer totalPage;
    private Integer total;
    private T list;
}
