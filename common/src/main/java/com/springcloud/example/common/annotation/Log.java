package com.springcloud.example.common.annotation;

import java.lang.annotation.*;

/***
 *  @Author dengwei
 *  @Description: 日志
 *  @Date 2018/10/15 9:45
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    String model() default "";
    String action() default "";
}
