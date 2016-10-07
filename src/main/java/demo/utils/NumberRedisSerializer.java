package demo.utils;

import java.nio.charset.*;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * @author xp
 */
public class NumberRedisSerializer implements RedisSerializer<Number> {

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
}
