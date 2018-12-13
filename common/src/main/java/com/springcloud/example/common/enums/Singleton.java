package com.springcloud.example.common.enums;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import com.springcloud.example.common.util.CalendarUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/***
 *  @Author dengwei
 *  @Description: 枚举单利
 *  枚举本身就是单利  可以直接实现相关逻辑 不用再次创建
 *  @Date 2018/9/19 17:50
 */
@AllArgsConstructor
@Getter
@Slf4j
public enum Singleton {
    INSTANCE;
    private CalendarUtil calendarUtil;
    private Cache<String,List<String>> cache;
    private List<String> codes = new ArrayList<>();
    Singleton(){
        calendarUtil = new CalendarUtil();
        cache = CacheBuilder.newBuilder()
                .expireAfterAccess(60, TimeUnit.SECONDS)
                .build();
    }
    public CalendarUtil getInstance(){
        return calendarUtil;
    }

    public void setCodes(List<String> codes) {
        this.codes = codes;
    }

    /**
     * guava 缓存
     * 说明 get方法 Callable 方法会去验证缓存中是否存在
     * 如果存在直接返回
     * 不存在执行加缓存操作
     * @param key
     * @return
     * @throws ExecutionException
     */
    public List<String> get(String key) throws ExecutionException {
        return cache.get(key, this::creteCacheData);
    }

    private List<String> creteCacheData() {
        log.info("创建！");
        return Lists.newArrayList("1","2","3");
    }
}
