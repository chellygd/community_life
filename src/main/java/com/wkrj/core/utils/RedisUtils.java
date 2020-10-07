package com.wkrj.core.utils;


import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * redis操作工具类
 *
 * @author ziro
 * @date 2020/5/19 19:30
 */
@Component
public class RedisUtils {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    public Object get(final String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 写入缓存（不过期）
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value) {
        boolean result = false;
        try {
            redisTemplate.opsForValue().set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入缓存（默认2分钟过期）
     *
     * @param key
     * @param value
     * @return
     */
    public boolean setM2(final String key, Object value) {
        boolean result = false;
        try {
            redisTemplate.opsForValue().set(key, value, 2, TimeUnit.MINUTES);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入缓存（超时时间，单位s）
     *
     * @param key
     * @param value
     * @param timeout 超时时间，单位s
     * @return
     */
    public boolean set(final String key, Object value, Long timeout) {
        boolean result = false;
        try {
            redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入缓存（加到期时间）
     *
     * @param key
     * @param value
     * @param endtime 到期时间
     * @return
     */
    public boolean set(final String key, Object value, Date endtime) {
        boolean result = false;
        try {
            Long timeout = DateUtil.between(DateUtil.date(), endtime, DateUnit.SECOND);
            redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 更新缓存超期时间
     *
     * @param key
     * @param timeout 超时时间，单位s
     * @return
     */
    public boolean setExpire(final String key, Long timeout) {
        boolean result = false;
        try {
            redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 删除缓存
     */
    public boolean delete(final String key) {
        boolean result = false;
        try {
            redisTemplate.delete(key);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        DateUtil.parseDate(DateUtil.tomorrow().toString());

    }
}
