package demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

import demo.config.properties.RedisProperties;
import demo.utils.GenericToStringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author xp
 */
@Configuration
@Import({PropertyConfig.class, JacksonConfig.class})
public class RedisConfig {

    @Autowired
    RedisProperties properties;

    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMinIdle(0);
        config.setMaxIdle(4);
        config.setMaxTotal(16);
        return config;
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory(RedisProperties properties, JedisPoolConfig poolConfig) {
        JedisConnectionFactory factory = new JedisConnectionFactory(poolConfig);
        factory.setHostName(properties.getHost());
        factory.setPort(properties.getPort());
        factory.setUsePool(true);
        return factory;
    }

    @Bean
    public GenericToStringRedisSerializer stringRedisSerializer() {
        return new GenericToStringRedisSerializer();
    }

    @Bean
    public JdkSerializationRedisSerializer jdkRedisSerializer() {
        return new JdkSerializationRedisSerializer();
    }

    @Bean
    public GenericJackson2JsonRedisSerializer jacksonRedisSerializer(ObjectMapper objectMapper) {
        return new GenericJackson2JsonRedisSerializer(objectMapper);
    }

    @Bean
    @Primary
    public <T> RedisTemplate<String, T> redisTemplate(
            RedisConnectionFactory connectionFactory,
            @Qualifier("stringRedisSerializer") RedisSerializer stringSerializer,
            @Qualifier("jdkRedisSerializer") RedisSerializer jdkSerializer,
            @Qualifier("jacksonRedisSerializer") RedisSerializer jsonSerializer) {
        RedisTemplate<String, T> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setEnableTransactionSupport(false);
        redisTemplate.setEnableDefaultSerializer(false);
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(jdkSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(jdkSerializer);
        return redisTemplate;
    }

}
