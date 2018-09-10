package com.springcloud.example.common.util;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {
    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 设置缓存 value型
     * @param key
     * @param value
     * @param time
     */
    public void set(Object key,Object value,Long time){
        ValueOperations valueOperations = redisTemplate.opsForValue();
        if (time == null){
            valueOperations.set(key,value);
        } else {
            valueOperations.set(key,value,time, TimeUnit.SECONDS);
        }
    }

    /**
     * 获取值  value型
     * @param key
     * @return
     */
    public Object get(Object key){
        ValueOperations valueOperations = redisTemplate.opsForValue();
        Object value = valueOperations.get(key);
        return value;
    }

    /**
     * 删除缓存
     * @param key
     */
    public void delete(Object key){
        redisTemplate.delete(key);
    }

    /**
     * 批量删除
     * @param keyPre key前缀
     */
    public void batchDelete(String keyPre){
        Set<Object> keys = redisTemplate.keys(keyPre + "*");
        for (Object key : keys){
            redisTemplate.delete(key);
        }
    }

    /**
     * 设置hash
     * @param key
     * @param hashKey
     * @param value
     */
    public void setHash(String key,String hashKey,Object value){
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.put(key,hashKey,value);
    }

    /**
     * @param key
     * @return hash中key对应的field->value
     */
    public Map getHash(String key){
        HashOperations hashOperations = redisTemplate.opsForHash();
        Map entries = hashOperations.entries(key);
        return entries;
    }

    /**
     * @param key
     * @param feild
     * @return hash 根据key field 获取value
     */
    public Object getHashByField(String key,String feild){
        HashOperations hashOperations = redisTemplate.opsForHash();
        Object value = hashOperations.get(key, feild);
        return value;
    }

    /**
     * 设置过期时间
     * @param key
     * @param timeOut
     */
    public void setExpire(String key,long timeOut){
        redisTemplate.expire(key,timeOut,TimeUnit.SECONDS);
    }

    /**
     * list设置
     * @param key
     * @param value
     */
    public void setListValue(String key,Object value){
        ListOperations listOperations = redisTemplate.opsForList();
        listOperations.rightPush(key,value);
    }

    /**
     * list设置 批量
     * @param key
     * @param value
     */
    public void setListValue(String key,Object... value){
        ListOperations listOperations = redisTemplate.opsForList();
        listOperations.leftPushAll(key,value);
    }

    /**
     * 获取list
     * list
     * @param key
     */
    public List getListValue(String key,Integer begin,Integer end){
        ListOperations listOperations = redisTemplate.opsForList();
        List list = listOperations.range(key, begin, end);
        return list;
    }
}
