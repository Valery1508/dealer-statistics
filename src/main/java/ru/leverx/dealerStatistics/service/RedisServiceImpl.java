package ru.leverx.dealerStatistics.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl {
    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public RedisServiceImpl(RedisTemplate<String, String> redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    public void addCodeToRedis(String key, String code){
        redisTemplate.opsForValue().set(key, code, 1, TimeUnit.DAYS);
    }

    public boolean checkCodeFromRedis(String key, String code){
        return code.equals(redisTemplate.opsForValue().get(key));
    }
}
