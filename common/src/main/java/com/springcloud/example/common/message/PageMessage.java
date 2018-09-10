package com.springcloud.example.common.message;

import lombok.Data;

import java.util.List;

@Data
public class PageMessage<T> {
    private Integer pageNumber; // 页码
    private Integer pageSize; // 每页大小
    private Integer totalPage;// 总页数
    private List<T> list;
}
