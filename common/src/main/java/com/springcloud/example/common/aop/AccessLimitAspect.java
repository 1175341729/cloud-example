package com.springcloud.example.common.aop;

import com.google.common.util.concurrent.RateLimiter;
import com.springcloud.example.common.advice.exception.GlobalException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/***
 *  @Author dengwei
 *  @Description: 限流切面
 *  @Date 2018/10/9 10:20
 */
@Aspect
@Component
public class AccessLimitAspect {
    private RateLimiter rateLimiter = RateLimiter.create(10);

    @Pointcut("@annotation(com.springcloud.example.common.annotation.AccessLimit)")
    public void point(){

    }

    @Around(value = "point()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object obj;
        boolean acquire = rateLimiter.tryAcquire();
        if (acquire){
            obj = joinPoint.proceed();
        } else {
            throw new GlobalException("请稍后再试！");
        }
        return obj;
    }
}
