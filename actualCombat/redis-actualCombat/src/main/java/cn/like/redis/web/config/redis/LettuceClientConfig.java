package cn.like.redis.web.config.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.RedisClusterClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;


/**
 * lettuce 客户端配置
 *
 * @author like
 * @date 2021/5/31 16:38
 */
@Configuration
public class LettuceClientConfig {

    /** Redis服务器地址 */
    @Value("${spring.redis.host}")
    private String host;

    /** Redis服务端口 */
    @Value("${spring.redis.port}")
    private Integer port;

    /** Redis密码 */
    @Value("${spring.redis.password}")
    private String password;

    /** 是否需要SSL */
    @Value("${spring.redis.ssl}")
    private Boolean ssl;

    /** Redis默认库，一共0～15 */
    @Value("${spring.redis.database}")
    private Integer database;

    @Value("${spring.redis.cluster.nodes}")
    private List<String> nodes;


    /**
     * Lettuce连接配置（Redis单机版实例）
     *
     * @return {@link RedisClient}
     */
    @Bean(name = "redisClient")
    @ConditionalOnProperty(value = "spring.redis.lettuce.cluster.fire", havingValue = "false")
    public RedisClient redisClient() {
        RedisURI uri = RedisURI.Builder
                .redis(this.host, this.port)
                .withDatabase(this.database)
                .build();
        return RedisClient.create(uri);
    }

    @Bean(name = "redisClient")
    @ConditionalOnProperty(value = "spring.redis.lettuce.cluster.fire", havingValue = "true")
    public RedisClusterClient redisClusterClient() {
        RedisClusterClient redisClusterClient = RedisClusterClient
                .create(this.nodes.stream().map(RedisURI::create).collect(Collectors.toList()));
        return redisClusterClient;
    }

}