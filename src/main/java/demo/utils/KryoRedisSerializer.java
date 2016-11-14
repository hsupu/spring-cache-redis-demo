package demo.utils;

import java.io.*;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.pool.KryoPool;

/**
 * @author xp
 */
public class KryoRedisSerializer<T> implements RedisSerializer<T> {

    private KryoPool kryoPool;

    public KryoRedisSerializer(KryoPool kryoPool) {
        this.kryoPool = kryoPool;
    }

    @Override
    public byte[] serialize(final T o) throws SerializationException {
        return kryoPool.run(kryo -> {
            if (o == null) {
                return new byte[0];
            }
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            Output output = new Output(stream);
            kryo.writeClassAndObject(output, o);
            output.close();
            return stream.toByteArray();
        });
    }

    @Override
    public T deserialize(final byte[] bytes) throws SerializationException {
        if (bytes == null) {
            throw new NullPointerException("bytes is uninitialized array.");
        }
        if (bytes.length == 0) {
            return null;
        }
        return kryoPool.run(kryo -> {
            Input input = new Input(bytes);
            @SuppressWarnings("unchecked")
            T o = (T) kryo.readClassAndObject(input);
            input.close();
            return o;
        });
    }
}
