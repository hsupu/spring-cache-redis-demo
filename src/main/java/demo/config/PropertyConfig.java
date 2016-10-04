package demo.config;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

/**
 * @author xp
 */
@Configuration
@ComponentScan("demo.config.properties")
public class PropertyConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        configurer.setIgnoreResourceNotFound(true);

        YamlPropertiesFactoryBean yamlPropertiesFactory = new YamlPropertiesFactoryBean();
        yamlPropertiesFactory.setResources(new ClassPathResource("redis.yaml"));
        configurer.setProperties(yamlPropertiesFactory.getObject());

        return configurer;
    }

}
