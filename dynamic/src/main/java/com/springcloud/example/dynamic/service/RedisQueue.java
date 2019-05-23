package com.springcloud.example.dynamic.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/***
 *  @Author dengwei
 *  @Description: redis 简单消息队列 --> 通过list类型实现
 *  @Date 2019/5/23 15:56
 */

@Service
@Slf4j
public class RedisQueue implements CommandLineRunner{
    @Resource
    private ListOperations listOperations;

    @Override
    public void run(String... args) {
        log.info("start.....");
        while (true){
            Object queue = listOperations.leftPop("queue");
            if (queue != null){
                log.info("{}", queue);
            }
        }
    }
}
