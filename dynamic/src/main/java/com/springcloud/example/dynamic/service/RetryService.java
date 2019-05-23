package com.springcloud.example.dynamic.service;

import cn.hutool.core.util.IdUtil;
import cn.hutool.http.HttpException;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONObject;
import com.springcloud.example.common.advice.exception.GlobalException;
import jdk.nashorn.internal.objects.Global;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/***
 *  @Author dengwei
 *  @Description: 接口重试
 *  @Date 2018/12/7 10:37
 */
@Component
@Slf4j
public class RetryService {
    private ThreadLocal<String> threadLocal = new ThreadLocal<>();

    @Retryable(value = RuntimeException.class, maxAttempts = 3, backoff = @Backoff(delay = 1000L, multiplier = 1))
    @Transactional
    public String retry(JSONObject param){
        log.info("{}",param);
        log.info("before:{}",param.getString("uuid"));
        threadLocal.set(param.getString("uuid"));
        try {
            String body = HttpRequest.get("http://localhost:11112/api/summary/device?supplier=a0065").execute().body();
        } catch (HttpException e) {
            throw new GlobalException("调用第三方系统异常！");
        }
        return "Ok";
    }

    @Recover
    public String recover(RuntimeException exception){
        String uuid = threadLocal.get();
        log.info("after:{}",uuid);
        log.info("重试次数超过限制！");
        threadLocal.remove();
        return "recover";
    }
}
