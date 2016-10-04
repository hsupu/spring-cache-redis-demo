package demo.utils;

import java.nio.charset.*;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.Assert;

/**
 * @author xp
 */
public class GenericToStringRedisSerializer implements RedisSerializer<Object> {

    private final Charset charset;

    public GenericToStringRedisSerializer() {
        this(Charset.forName("UTF8"));
    }

    public GenericToStringRedisSerializer(Charset charset) {
        Assert.notNull(charset);
        this.charset = charset;
    }

    public String deserialize(byte[] bytes) {
        if (RedisSerializerUtils.isEmpty(bytes)) {
            return null;
        }
        return new String(bytes, charset);
    }

    public byte[] serialize(Object object) {
        if (object == null) {
            return RedisSerializerUtils.EMPTY_ARRAY;
        }
        return object.toString().getBytes(charset);
    }

}
