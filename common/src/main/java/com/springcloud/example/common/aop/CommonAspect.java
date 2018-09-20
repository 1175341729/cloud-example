package com.springcloud.example.common.aop;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/***
 *  @Author dengwei
 *  @Description: TODO
 *  @Date 2018/9/13 13:35
 */
@Aspect
@Component
@Slf4j
public class CommonAspect {
    @Pointcut(value = "execution(* com.springcloud.example.*.controller.*.*(..))")
    public void point(){

    }

    @Around(value = "point()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        String method = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        log.info("请求方法：{}",method);
        log.info("请求参数：{}", JSON.toJSONString(args));
        Long begin = System.currentTimeMillis();
        Object rsp = joinPoint.proceed();
        log.info("请求耗时：{}",System.currentTimeMillis() - begin);
        return rsp;
    }
}
