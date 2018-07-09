package com.nearbuy.dynamic.pricing.dynamicpricing.Config;

import com.nearbuy.dynamic.pricing.dynamicpricing.util.AppUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class CacheManager {
    private static final long DEFAULT_EXPIRY_TIME = 10000l;
    private static  Logger logger = LoggerFactory.getLogger(CacheManager.class);
    public static int maxSize = 1000;


    static void updateMaxSize(int maxSize){
        CacheManager.maxSize = maxSize;
        map.clear();
    }

    static Map<String, CerebroCachable> map = Collections.synchronizedMap(new LinkedHashMap(maxSize){
        @Override
        protected boolean removeEldestEntry(Map.Entry eldest) {
            return size() > maxSize;
        }
    });

    public static Object get(String key) {
        if (map.containsKey(key)) {
            CerebroCachable val = map.remove(key);
            if(val == null){
                return null;
            }
            if(hasExpired(val)){
                logger.info("cache expired  key : {}", key);
                return null;
            }
            map.put(key, val);
            logger.info("got response from cache : {}", key);
            return val.getValue();
        }
        logger.info("could not find in cache : {}", key);
        return null;
    }

    private static boolean hasExpired(CerebroCachable val) {
        return val.getExpiresAt() < AppUtil.currentTime();
    }

    public static void add(Object val, String key){
        add(val, key, DEFAULT_EXPIRY_TIME);
    }


    public static void add(Object val, String key, long expiresIn){
        if(map.containsKey(key)){
            map.remove(key);
        }
        map.put(key, new CerebroCachable(val, expiresIn + AppUtil.currentTime()));
        logger.info("added to cache : {}", key);
    }

    private static class CerebroCachable {
        private Object value;
        Long expiresAt;

        public CerebroCachable(Object value, Long expiresAt) {
            this.value = value;
            this.expiresAt = expiresAt;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public Long getExpiresAt() {
            return expiresAt;
        }

        public void setExpiresAt(Long expiresAt) {
            this.expiresAt = expiresAt;
        }

        public Object getValue() {
            return value;
        }
    }


}
