package com.springcloud.example.dynamic.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

/***
 *  @Author dengwei
 *  @Description: 接口重试
 *  @Date 2018/12/7 10:37
 */
@Component
@Slf4j
public class RetryService {
    public static int number = 4;

    @Retryable(value = RuntimeException.class, maxAttempts = 5, backoff = @Backoff(delay = 1000L, multiplier = 1))
    public String retry(){
        --number;
        if (number > 0)
        throw new RuntimeException("发生异常！");

        return "Ok";
    }

    @Recover
    public String recover(RuntimeException exception){
        log.info("重试次数超过限制！");
        return "recover";
    }
}
