package com.example.read_write_db.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by Sherif.Abdulraheem 19/03/2025 - 17:07
 **/
@Service
@RequiredArgsConstructor
public class RedisUtility {
    private @Autowired RedisTemplate<String, Object> redisTemplate;
    private @Autowired RedisTemplate<String, Integer> integerRedisTemplate;
    private static final String REDIS_PREFIX = "ReadWriteService:";

    private String getRedisKey(String key){
        return REDIS_PREFIX + key;
    }
    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(getRedisKey(key)));
    }

    public void setValue(String key, Object value, Boolean isInteger) {
        if(isInteger){
            integerRedisTemplate.opsForValue().set(getRedisKey(key), (Integer) value);
            return;
        }

        redisTemplate.opsForValue().set(getRedisKey(key), value);
    }

    public void setValueWithExpiration(String key, Object value, Long expiration, TimeUnit timeUnit, Boolean isInteger) {
        if(isInteger){
            integerRedisTemplate.opsForValue().set(getRedisKey(key), (Integer) value);
            integerRedisTemplate.expire(getRedisKey(key), expiration, timeUnit);
            return;
        }

        redisTemplate.opsForValue().set(getRedisKey(key), value);
        redisTemplate.expire(getRedisKey(key), expiration, timeUnit);
    }

    public Object getValue(String key, Boolean isInteger) {
        return !isInteger ? redisTemplate.opsForValue().get(getRedisKey(key)) : integerRedisTemplate.opsForValue().get(getRedisKey(key));
    }

    public Object getValue(String key) {
        return redisTemplate.opsForValue().get(getRedisKey(key));
    }

    public void deleteKeyAndValue(final String key) {
        redisTemplate.delete(getRedisKey(key));
    }

    public long increment(String key) {
        return integerRedisTemplate.opsForValue().increment(getRedisKey(key));
    }

    public void setValueWithHash(String key, String hashKey, Object value, Boolean isInteger) {
        if(isInteger){
            integerRedisTemplate.opsForHash().put(getRedisKey(key), hashKey, value);
        }

        redisTemplate.opsForHash().put(getRedisKey(key), hashKey, value);
    }

    public void setValueWithHashAndExpiration(String key, String hashKey, Object value, Long expiration, TimeUnit timeUnit, Boolean isInteger) {
        if(isInteger){
            integerRedisTemplate.opsForHash().put(getRedisKey(key), hashKey, value);
            integerRedisTemplate.expire(getRedisKey(key), expiration, timeUnit);

            return;
        }

        redisTemplate.opsForHash().put(getRedisKey(key), hashKey, value);
        redisTemplate.expire(getRedisKey(key), expiration, timeUnit);
    }

    public Object getValueUsingHash(String key, String hash, Boolean isInteger) {
        return !isInteger ? redisTemplate.opsForHash().get(getRedisKey(key), hash) : integerRedisTemplate.opsForHash().get(getRedisKey(key), hash);
    }

    public void deleteKeysByPattern(String pattern) {
        String redisPattern = getRedisKey(pattern) + "*";
        Set<String> keys = redisTemplate.keys(redisPattern);
        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
    }
}
