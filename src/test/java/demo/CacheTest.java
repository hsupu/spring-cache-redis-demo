package demo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import demo.cache.SampleCache;
import demo.config.MyCacheConfig;
import demo.config.properties.RedisProperties;

/**
 * @author xp
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {MyCacheConfig.class})
public class CacheTest {

    private static final Logger LOG = LoggerFactory.getLogger(CacheTest.class);

    @Autowired
    RedisProperties redisProperties;

    @Autowired
    SampleCache sampleCache;

    @Test
    public void configTest() {
        LOG.info(redisProperties.toString());
    }

    @Test
    public void cacheableTest() {
        Assert.assertEquals("1", sampleCache.get(1));
        Assert.assertEquals("2", sampleCache.set(1, "2"));
        Assert.assertEquals("2", sampleCache.get(1));
        Assert.assertEquals(true, sampleCache.del(1));
        Assert.assertEquals("1", sampleCache.get(1));
    }

}
