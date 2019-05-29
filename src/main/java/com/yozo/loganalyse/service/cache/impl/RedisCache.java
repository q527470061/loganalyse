package com.yozo.loganalyse.service.cache.impl;

import com.yozo.loganalyse.service.cache.Cache;
import com.yozo.loganalyse.commons.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 存取临时数据Redis形式
 */
@Service
public class RedisCache implements Cache {

    @Autowired
    RedisUtil redisUtil;

    @Override
    public Boolean saveLine(String key, int line, int second) {
        return redisUtil.set(key,line,second);
    }

    @Override
    public int readLastLine(String key) {
        return (int) redisUtil.get(key);
    }

    @Override
    public boolean existKey(String key) {
        return redisUtil.hasKey(key);
    }
}
