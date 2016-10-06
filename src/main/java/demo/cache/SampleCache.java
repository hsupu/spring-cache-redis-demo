package demo.cache;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author xp
 */
@Component
@CacheConfig(cacheNames = SampleCache.cacheName, keyGenerator = "cacheKeyGenerator")
public class SampleCache {

    private static final Logger LOG = LoggerFactory.getLogger(SampleCache.class);

    static final String cacheName = "Sample";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Cacheable(key = "#id.toString()", sync = true)
    public String get(Integer id) {
        String result = id.toString();
        LOG.info(String.format("set %d: %s", id, result));
        return result;
    }

    @CachePut(key = "#id.toString()")
    public String set(Integer id, String data) {
        String result = data;
        LOG.info(String.format("set %d: %s", id, result));
        return result;
    }

    @CacheEvict(key = "#id.toString()")
    public boolean del(Integer id) {
        LOG.info(String.format("del %d", id));
        return true;
    }

    public void clear() {
        Set<String> keys = redisTemplate.keys(cacheName + ":*");
        redisTemplate.delete(keys);
    }

}
