package com.yhguo.web_poms.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 存键值对
     *
     * @param key   键
     * @param value 值
     */
    public void set(String key, String value) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value);
    }

    /**
     * 存储键值对，并且设置有效期
     *
     * @param key   键
     * @param value 值
     * @param time  时间
     */
    public void setAndTime(String key, String value, long time) {
        set(key, value);
        redisTemplate.expire(key, time, TimeUnit.SECONDS);
    }

    /**
     * 获取键的过期时间
     *
     * @param key 键
     * @return time 过期时间
     */
    public long getExpireTime(String key) {
        long time = redisTemplate.getExpire(key);
        return time;
    }


    /**
     * 读取redis
     *
     * @param key
     * @return result 结果
     */
    public String get(String key) {
        Object result = null;
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return (String) result;
    }

}