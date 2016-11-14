package demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.pool.KryoFactory;
import com.esotericsoftware.kryo.pool.KryoPool;
import com.esotericsoftware.kryo.serializers.CompatibleFieldSerializer;

/**
 * @author xp
 */
@Configuration
public class KryoConfig {

    @Bean
    public KryoPool kryoPool() {
        KryoFactory factory = () -> {
            Kryo kryo = new Kryo();
            kryo.setDefaultSerializer(CompatibleFieldSerializer.class);
            return kryo;
        };
        return new KryoPool.Builder(factory).softReferences().build();
    }

}
