package demo.config;

import java.util.*;
import java.util.stream.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author xp
 */
@Configuration
@Import(RedisConfig.class)
@EnableCaching
@ComponentScan(basePackages = "demo.cache")
public class MyCacheConfig {

    private static final Logger LOG = LoggerFactory.getLogger(MyCacheConfig.class);

    @Bean
    public RedisCacheManager redisCacheManager(RedisTemplate<String, Object> redisTemplate) {
        RedisCacheManager redisCacheManager = new RedisCacheManager(redisTemplate);
        redisCacheManager.setDefaultExpiration(1024);  // 10 min
        redisCacheManager.setUsePrefix(true);
        //redisCacheManager.setTransactionAware(false);
        //redisCacheManager.afterPropertiesSet();
        return redisCacheManager;
    }

    @Bean
    @Primary
    public CacheManager cacheManager(RedisCacheManager redisCacheManager) {
        return redisCacheManager;
    }

    @Bean
    public KeyGenerator cacheKeyGenerator() {
        return (target, method, params) -> {
            if (params.length == 0) {
                return "";
            } else {
                List<String> keys = Arrays.stream(params).map(Object::toString).collect(Collectors.toList());
                return String.join(":", keys);
            }
        };
    }

}
