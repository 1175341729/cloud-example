package com.springcloud.example.common.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.springcloud.example.common.advice.exception.GlobalException;
import com.springcloud.example.common.annotation.Lock;
import com.springcloud.example.common.annotation.Log;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisCommands;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/***
 *  @Author dengwei
 *  @Description: 记录日志切面
 *  @Date 2018/9/13 13:35
 */
@Aspect
@Component
@Slf4j
public class CommonAspect {
    @Resource
    private RedisTemplate redisTemplate;
    @Pointcut(value = "execution(* com.springcloud.example.*.controller.*.*(..))")
    public void point(){

    }

    @Around(value = "point()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Signature signature = joinPoint.getSignature();
        String className = signature.getDeclaringTypeName() + "." + signature.getName();
        Object[] args = joinPoint.getArgs();
        StringBuilder params = new StringBuilder();
        for(Object arg : args){
            String param;
            if (arg instanceof HttpServletResponse){
                param = HttpServletResponse.class.getSimpleName();
            } else if (arg instanceof HttpServletRequest){
                param = HttpServletRequest.class.getSimpleName();
            } else if (arg instanceof MultipartFile){
                param = MultipartFile.class.getSimpleName();
            } else {
                param = JSON.toJSONString(arg);
            }
            params.append(param).append(";");
        }
        log.info("请求方法：{}",className);
        log.info("请求参数：{}", StringUtils.removeEndIgnoreCase(params.toString(),";"));

        // 通过自定义注解
        MethodSignature methodSignature = (MethodSignature) signature;
        Method  method = methodSignature.getMethod();
        if (method != null){
            Log logInfo = method.getAnnotation(Log.class);
            if (logInfo != null){
                log.info("请求模块：{}",logInfo.model());
                log.info("请求操作：{}",logInfo.action());
            }
        }
        Long begin = System.currentTimeMillis();
        Object rsp = joinPoint.proceed();
        log.info("请求耗时：{}",System.currentTimeMillis() - begin);
        return rsp;
    }

    /**
     * redis分布式加锁
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around(value = "@annotation(com.springcloud.example.common.annotation.Lock)")
    public Object lockAround(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Lock lock = methodSignature.getMethod().getAnnotation(Lock.class);
        String requestId = lock.requestId();
        long expiration = lock.expiration();
        JSONObject data = JSON.parseObject(JSON.toJSONString(joinPoint.getArgs()[0]));

        String key = data.getString(requestId);
        String result = (String) redisTemplate.execute((RedisCallback) connection -> {
            JedisCommands commands = (JedisCommands) connection.getNativeConnection();
            return commands.set(key, "lock", "NX", "EX", expiration);
        });
        if (!"OK".equalsIgnoreCase(result)){
            throw new GlobalException("请稍后重试！");
        }
        Object proceed = joinPoint.proceed();
        // redisTemplate.delete(key);
        return proceed;
    }
}
