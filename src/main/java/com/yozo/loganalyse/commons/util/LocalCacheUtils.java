package com.yozo.loganalyse.commons.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class LocalCacheUtils<T> {

    private final ConcurrentHashMap<String, T> concurrentMap = new ConcurrentHashMap();

    public Boolean set(String key, T value) {
        try {
            if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) {
                return false;
            }
            concurrentMap.put(key, value);
            return true;
        } catch (Exception e) {
            log.error("com.yozo.loganalyse.commons.util.LocalCacheUtils.set,ERROR:{}", e);
            return false;
        }
    }

    public T get(String key) {
        try {
            if (StringUtils.isEmpty(key)) {
                return null;
            }
            return concurrentMap.get(key);
        } catch (Exception e) {
            log.error("com.yozo.loganalyse.commons.util.LocalCacheUtils.get,ERROR:{}", e);
            return null;
        }
    }

    public boolean exists(String key) {
        try {
            if (StringUtils.isEmpty(key)) {
                return false;
            }
            return concurrentMap.containsKey(key);
        } catch (Exception e) {
            log.error("com.yozo.loganalyse.commons.util.LocalCacheUtils.exists,ERROR:{}", e);
            return false;
        }
    }

    public boolean clearAll() {
        try {
            concurrentMap.clear();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("com.yozo.loganalyse.commons.util.LocalCacheUtils.clearAll,ERROR:{}", e);
            return false;
        }
    }

    public boolean delete(String key) {
        try {
            if (StringUtils.isEmpty(key)) {
                return false;
            }
            concurrentMap.remove(key);
            return true;
        } catch (Exception e) {
            log.error("com.yozo.loganalyse.commons.util.LocalCacheUtils.delete,ERROR:{}", e);
            return false;
        }
    }

    public static void main(String[] args){

    }
}
