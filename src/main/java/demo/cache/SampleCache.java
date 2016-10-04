package demo.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * @author xp
 */
@Component
@CacheConfig(cacheNames = SampleCache.cacheName, keyGenerator = "cacheKeyGenerator")
public class SampleCache {

    private static final Logger LOG = LoggerFactory.getLogger(SampleCache.class);

    public static final String cacheName = "Sample";

    @Cacheable(key = "#id", sync = true)
    public String get(Integer id) {
        String result = id.toString();
        LOG.info(String.format("set %d: %s", id, result));
        return result;
    }

    @CachePut(key = "#id")
    public String set(Integer id, String data) {
        String result = data;
        LOG.info(String.format("set %d: %s", id, result));
        return result;
    }

    @CacheEvict(key = "#id")
    public boolean del(Integer id) {
        LOG.info(String.format("del %d", id));
        return true;
    }

}
