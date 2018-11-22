package com.springcloud.example.dynamic.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import java.util.Date;

/***
 *  @Author dengwei
 *  @Description: TODO
 *  @Date 2018/9/10 17:23
 */
@Data
public class StudentReq {
    @NotBlank(message = "name不能为空！")
    private String name;
    @Min(value = 10,message = "需大于{value}")
    private Integer age;
    private Date date = new Date();
}
