package demo.config;

import java.io.*;
import java.nio.charset.*;

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
import demo.utils.RedisSerializerUtils;
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
    public RedisSerializer<Number> numberRedisSerializer() {
        return new RedisSerializer<Number>() {

            private Charset charset = Charset.forName("UTF8");

            @Override
            public byte[] serialize(Number o) throws SerializationException {
                if (o == null) {
                    return RedisSerializerUtils.EMPTY_ARRAY;
                }
                return o.toString().getBytes(charset);
            }

            @Override
            public Long deserialize(byte[] bytes) throws SerializationException {
                if (RedisSerializerUtils.isEmpty(bytes)) {
                    return null;
                }
                return Long.valueOf(new String(bytes, charset));
            }
        };
    }

    @Bean
    @Primary
    public <T extends Serializable> RedisTemplate<String, T> redisTemplate(
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

    @Bean
    public <T extends Number> RedisTemplate<String, T> numberRedisTemplate(
            RedisConnectionFactory connectionFactory,
            @Qualifier("stringRedisSerializer") RedisSerializer stringSerializer,
            @Qualifier("numberRedisSerializer") RedisSerializer numberSerializer) {
        RedisTemplate<String, T> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setEnableTransactionSupport(false);
        redisTemplate.setEnableDefaultSerializer(false);
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(numberSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(numberSerializer);
        return redisTemplate;
    }

}
