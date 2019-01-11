package com.springcloud.example.dynamic.message;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/***
 *  @Author dengwei
 *  @Description: TODO
 *  @Date 2019/1/11 15:44
 */
@Data
public class TaskDetailReq {
    private Long id;
    @NotNull(message = "屏幕类型不能为空！")
    @Min(value = 1)
    private Byte screenType;// 屏幕类型
    @NotNull(message = "数量不能为空！")
    @Min(value = 0)
    private Integer targetNum;// 数量
    @NotNull(message = "目标项不能为空！")
    @Min(value = 0)
    private Byte order;// 月、周 编号
}
