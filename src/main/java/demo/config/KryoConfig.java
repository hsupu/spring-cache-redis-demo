package demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.pool.KryoFactory;
import com.esotericsoftware.kryo.pool.KryoPool;

/**
 * @author xp
 */
@Configuration
public class KryoConfig {

    @Bean
    public KryoPool kryoPool() {
        KryoFactory factory = Kryo::new;
        return new KryoPool.Builder(factory).softReferences().build();
    }

}
