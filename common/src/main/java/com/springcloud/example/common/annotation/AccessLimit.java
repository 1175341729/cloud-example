package com.springcloud.example.common.annotation;

import java.lang.annotation.*;

/***
 *  @Author dengwei
 *  @Description: 限流注解
 *  @Date 2018/10/9 10:10
 */
@Inherited
@Documented
@Target({ElementType.METHOD,ElementType.FIELD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessLimit {
}
