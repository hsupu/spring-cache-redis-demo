package demo.config.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author xp
 */
@Component
public class RedisProperties {

    @Value("${redis.host}")
    private String host;

    @Value("${redis.port}")
    private Integer port;

    public String getHost() {
        return host;
    }

    public Integer getPort() {
        return port;
    }
}
