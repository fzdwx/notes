package cn.like.code.redis.config;

import cn.like.code.redis.properties.LettuceConfigProperties;
import cn.like.code.redis.service.RedisService;
import io.lettuce.core.AbstractRedisClient;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.support.ConnectionPoolSupport;
import lombok.Getter;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * desc:
 * details:
 *
 * @author: like
 * @since: 2021/6/6 12:14
 * @email: 980650920@qq.com
 */
@Configuration
@ConditionalOnClass(value = RedisService.class)
@AutoConfigureAfter(LettuceConnectionConfiguration.class)
public class LettucePoolConfiguration {

    private final LettuceConfigProperties properties;
    private final static Logger log = getLogger(LettucePoolConfiguration.class);

    @Resource(name = "redisClient")
    private AbstractRedisClient redisClient;

    /**
     * Apache-Common-Pool是一个对象池，用于缓存Redis连接，
     * 因为 lettuce 本身基于Netty的异步驱动，但基于Servlet模型的同步访问时，连接池是必要的
     * 连接池可以很好的复用连接，减少重复的IO消耗与RedisURI创建实例的性能消耗
     */
    @Getter
    GenericObjectPool<StatefulRedisConnection<String, String>> redisConnectionPool;

    @Getter
    GenericObjectPool<StatefulRedisClusterConnection<String, String>> redisClusterConnectionPool;

    public LettucePoolConfiguration(LettuceConfigProperties properties) {this.properties = properties;}

    /**
     * Servlet初始化时先初始化Lettuce连接池
     */
    @PostConstruct
    private void init() {
        final LettuceConfigProperties.Pool pool = properties.getPool();

        if (!properties.getFire()) {
            log.info("[redis 连接池 初始化]-[ 单体 ]");

            GenericObjectPoolConfig<StatefulRedisConnection<String, String>> redisPoolConfig
                    = new GenericObjectPoolConfig<>();
            redisPoolConfig.setMaxTotal(pool.getMaxTotal());
            redisPoolConfig.setMaxIdle(pool.getMaxIdle());
            redisPoolConfig.setMinIdle(pool.getMinIdle());
            redisPoolConfig.setMaxWaitMillis(pool.getMaxWait().toMillis());
            if (pool.getTestWhileIdle() != null) {
                redisPoolConfig.setTestWhileIdle(pool.getTestWhileIdle());
            }
            if (pool.getTestOnReturn() != null) {
                redisPoolConfig.setTestOnReturn(pool.getTestOnReturn());
            }
            if (pool.getTimeBetweenEvictionRuns() != null) {
                redisPoolConfig.setTimeBetweenEvictionRunsMillis(pool.getTimeBetweenEvictionRuns().toMillis());
            }
            this.redisConnectionPool =
                    ConnectionPoolSupport
                            .createGenericObjectPool(() -> ((RedisClient) redisClient).connect(), redisPoolConfig);
        } else {
            log.info("[redis 连接池 初始化]-[ 集群 ]");
            GenericObjectPoolConfig<StatefulRedisClusterConnection<String, String>> redisPoolConfig
                    = new GenericObjectPoolConfig<>();
            redisPoolConfig.setMaxTotal(pool.getMaxTotal());
            redisPoolConfig.setMaxIdle(pool.getMaxIdle());
            redisPoolConfig.setMinIdle(pool.getMinIdle());
            redisPoolConfig.setMaxWaitMillis(pool.getMaxWait().toMillis());
            if (pool.getTestWhileIdle() != null) {
                redisPoolConfig.setTestWhileIdle(pool.getTestWhileIdle());
            }
            if (pool.getTestOnReturn() != null) {
                redisPoolConfig.setTestOnReturn(pool.getTestOnReturn());
            }
            if (pool.getTimeBetweenEvictionRuns() != null) {
                redisPoolConfig.setTimeBetweenEvictionRunsMillis(pool.getTimeBetweenEvictionRuns().toMillis());
            }
            this.redisClusterConnectionPool =
                    ConnectionPoolSupport
                            .createGenericObjectPool(() -> ((RedisClusterClient) redisClient)
                                    .connect(), redisPoolConfig);
        }
    }

    /**
     * Servlet销毁时先销毁Lettuce连接池
     */
    @PreDestroy
    private void destroy() {
        log.info("[redis 连接 销毁]");

        if (!properties.getFire()) {
            redisConnectionPool.close();
        } else {
            redisClusterConnectionPool.close();
        }
        redisClient.shutdown();
    }

}
