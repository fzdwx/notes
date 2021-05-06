package cn.like.redis.customizeLettuce;

import cn.like.redis.customizeLettuce.properties.LettuceProperties;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulConnection;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.resource.ClientResources;
import io.lettuce.core.resource.DefaultClientResources;
import io.lettuce.core.support.ConnectionPoolSupport;
import lombok.RequiredArgsConstructor;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Configuration
@ConditionalOnClass(io.lettuce.core.RedisURI.class)
@EnableConfigurationProperties(value = LettuceProperties.class)
public class LettuceAutoConfiguration {

    private final LettuceProperties lettuceProperties;

    @Bean(destroyMethod = "shutdown")
    public ClientResources clientResources() {
        return DefaultClientResources.create();
    }

    // ======================= single ==================================================
    @Bean
    @ConditionalOnProperty(name = "lettuce.host")
    public RedisURI singleRedisUri() {
        return RedisURI.builder()
                       .withHost(lettuceProperties.getHost())
                       .withPort(lettuceProperties.getPort())
                       // .withPassword(singleProperties.getPassword())
                       .build();
    }

    @Bean(destroyMethod = "shutdown")
    @ConditionalOnProperty(name = "lettuce.host")
    public RedisClient singleRedisClient(ClientResources clientResources, @Qualifier("singleRedisUri") RedisURI redisUri) {
        return RedisClient.create(clientResources, redisUri);
    }

    @Bean(destroyMethod = "close")
    @ConditionalOnProperty(name = "lettuce.host")
    public StatefulRedisConnection<String, String> singleRedisConnection(@Qualifier("singleRedisClient") RedisClient singleRedisClient) {
        return singleRedisClient.connect();
    }

    // ====================== cluster ==================================================

    @Bean(destroyMethod = "shutdown")
    @ConditionalOnProperty(name = "lettuce.cluster.nodes")
    public RedisClusterClient redisClusterClient(ClientResources clientResources) {
        LettuceProperties.Cluster cluster = lettuceProperties.getCluster();

        return RedisClusterClient.create(clientResources, cluster.getNodes().stream().map(s -> {
            String[] split = s.split(":");
            String host = split[0];
            String port = split[1];
            return RedisURI.builder().withHost(host).withPort(Integer.parseInt(port)).build();
        }).collect(Collectors.toList()));
    }

    @Bean(destroyMethod = "close")
    @ConditionalOnProperty(name = "lettuce.cluster.nodes")
    public StatefulRedisClusterConnection<String, String> clusterConnection(RedisClusterClient clusterClient) {
        return clusterClient.connect();
    }

    // ====================== pool ==================================================
    @ConditionalOnProperty(name = "lettuce.host")
    @Bean(destroyMethod = "close")
    public GenericObjectPool<StatefulRedisConnection<String, String>> connectionPool(@Autowired RedisClient client,
                                                                                     @Autowired GenericObjectPoolConfig<? extends StatefulConnection<String, String>> poolConfig) {
        return ConnectionPoolSupport.createGenericObjectPool(
                client::connect,
                ((GenericObjectPoolConfig<StatefulRedisConnection<String, String>>) poolConfig)
        );
    }

    @ConditionalOnProperty(name = "lettuce.cluster.nodes")
    @Bean(destroyMethod = "close")
    public GenericObjectPool<StatefulRedisClusterConnection<String, String>> clusterConnectionPool(@Autowired RedisClusterClient clusterClient,
                                                                                                   @Autowired GenericObjectPoolConfig<? extends StatefulConnection<String, String>> poolConfig) {
        return ConnectionPoolSupport.createGenericObjectPool(
                clusterClient::connect,
                ((GenericObjectPoolConfig<StatefulRedisClusterConnection<String, String>>) poolConfig)
        );
    }

    @Bean
    public GenericObjectPoolConfig<? extends StatefulConnection<String, String>> poolConfig(LettuceProperties lettuceProperties) {
        LettuceProperties.Pool properties = lettuceProperties.getPool();

        GenericObjectPoolConfig<? extends StatefulConnection<String, String>> config = new GenericObjectPoolConfig<>();
        config.setMaxTotal(properties.getMaxActive());
        config.setMaxIdle(properties.getMaxIdle());
        config.setMinIdle(properties.getMinIdle());

        config.setJmxEnabled(false);

        if (properties.getTimeBetweenEvictionRuns() != null) {
            config.setTimeBetweenEvictionRunsMillis(properties.getTimeBetweenEvictionRuns().toMillis());
        }
        if (properties.getMaxWait() != null) {
            config.setMaxWaitMillis(properties.getMaxWait().toMillis());
        }
        return config;
    }
}