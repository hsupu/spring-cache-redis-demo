package demo.config;

import java.io.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;

import demo.config.properties.RedisProperties;
import demo.utils.KryoRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import com.esotericsoftware.kryo.pool.KryoPool;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author xp
 */
@Configuration
@Import({PropertyConfig.class, JacksonConfig.class, KryoConfig.class})
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
    public RedisSerializer<String> stringRedisSerializer() {
        return new StringRedisSerializer();
    }

    @Bean
    public RedisSerializer<Object> jdkRedisSerializer() {
        return new JdkSerializationRedisSerializer();
    }

    @Bean
    public RedisSerializer<Object> jacksonRedisSerializer(ObjectMapper objectMapper) {
        return new GenericJackson2JsonRedisSerializer(objectMapper);
    }

    @Bean
    public <T> RedisSerializer<T> kryoRedisSerializer(KryoPool kryoPool) {
        return new KryoRedisSerializer<>(kryoPool);
    }

    @Bean
    public RedisSerializer<Number> numberRedisSerializer() {
        return new GenericToStringSerializer<>(Number.class);
    }

    @Bean
    public <T extends Serializable> RedisTemplate<String, T> jdkRedisTemplate(
            RedisConnectionFactory connectionFactory,
            @Qualifier("stringRedisSerializer") RedisSerializer keySerializer,
            @Qualifier("jdkRedisSerializer") RedisSerializer valueSerializer) {
        RedisTemplate<String, T> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setEnableTransactionSupport(false);
        redisTemplate.setEnableDefaultSerializer(false);
        redisTemplate.setKeySerializer(keySerializer);
        redisTemplate.setHashKeySerializer(keySerializer);
        redisTemplate.setValueSerializer(valueSerializer);
        redisTemplate.setHashValueSerializer(valueSerializer);
        return redisTemplate;
    }

    @Bean
    @Primary
    public <T extends Serializable> RedisTemplate<String, T> kryoRedisTemplate(
            RedisConnectionFactory connectionFactory,
            @Qualifier("stringRedisSerializer") RedisSerializer keySerializer,
            @Qualifier("kryoRedisSerializer") RedisSerializer valueSerializer) {
        RedisTemplate<String, T> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setEnableTransactionSupport(false);
        redisTemplate.setEnableDefaultSerializer(false);
        redisTemplate.setKeySerializer(keySerializer);
        redisTemplate.setHashKeySerializer(keySerializer);
        redisTemplate.setValueSerializer(valueSerializer);
        redisTemplate.setHashValueSerializer(valueSerializer);
        return redisTemplate;
    }

    @Bean
    public <T extends Serializable> RedisTemplate<String, T> jacksonRedisTemplate(
            RedisConnectionFactory connectionFactory,
            @Qualifier("stringRedisSerializer") RedisSerializer keySerializer,
            @Qualifier("jacksonRedisSerializer") RedisSerializer valueSerializer) {
        RedisTemplate<String, T> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setEnableTransactionSupport(false);
        redisTemplate.setEnableDefaultSerializer(false);
        redisTemplate.setKeySerializer(keySerializer);
        redisTemplate.setHashKeySerializer(keySerializer);
        redisTemplate.setValueSerializer(valueSerializer);
        redisTemplate.setHashValueSerializer(valueSerializer);
        return redisTemplate;
    }

    @Bean
    public RedisTemplate<String, String> stringRedisTemplate(
            RedisConnectionFactory connectionFactory,
            @Qualifier("stringRedisSerializer") RedisSerializer keySerializer,
            @Qualifier("stringRedisSerializer") RedisSerializer valueSerializer) {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setEnableTransactionSupport(false);
        redisTemplate.setEnableDefaultSerializer(false);
        redisTemplate.setKeySerializer(keySerializer);
        redisTemplate.setHashKeySerializer(keySerializer);
        redisTemplate.setValueSerializer(valueSerializer);
        redisTemplate.setHashValueSerializer(valueSerializer);
        return redisTemplate;
    }

    @Bean
    public <T extends Number> RedisTemplate<String, T> numberRedisTemplate(
            RedisConnectionFactory connectionFactory,
            @Qualifier("stringRedisSerializer") RedisSerializer keySerializer,
            @Qualifier("numberRedisSerializer") RedisSerializer valueSerializer) {
        RedisTemplate<String, T> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setEnableTransactionSupport(false);
        redisTemplate.setEnableDefaultSerializer(false);
        redisTemplate.setKeySerializer(keySerializer);
        redisTemplate.setHashKeySerializer(keySerializer);
        redisTemplate.setValueSerializer(valueSerializer);
        redisTemplate.setHashValueSerializer(valueSerializer);
        return redisTemplate;
    }

}
