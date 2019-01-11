package com.springcloud.example.dynamic.message;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/***
 *  @Author dengwei
 *  @Description: TODO
 *  @Date 2019/1/11 15:24
 */
@Data
public class TaskBaseReq {
    @NotNull(message = "所属年份不能为空！")
    private Short year;
    private Byte month;
    @NotBlank(message = "所属公司不能为空！")
    private String company;
    private Byte progressType = 1;
    @Valid
    private List<TaskDetailReq> detailList;
}
