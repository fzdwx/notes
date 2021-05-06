package cn.like.redis.customizeLettuce;

import cn.like.redis.customizeLettuce.properties.LettuceProperties;
import io.lettuce.core.ClientOptions;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulConnection;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;
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

    /**
     * 客户端 配置
     *
     * @return {@link ClientOptions}
     */
    @Bean
    public ClientOptions clientOptions() {
        ClientOptions.Builder builder = ClientOptions
                .builder()
                .autoReconnect(lettuceProperties.isAutoReconnect());
        return builder.build();

    }
    // ======================= single ==================================================

    /**
     * 单机redis url 配置
     *
     * @return {@link RedisURI}
     */
    @Bean
    @ConditionalOnProperty(name = "lettuce.host")
    public RedisURI singleRedisUri() {
        RedisURI.Builder builder = RedisURI.builder()
                .withHost(lettuceProperties.getHost())
                .withPort(lettuceProperties.getPort())
                .withDatabase(lettuceProperties.getDatabase());
        if (!lettuceProperties.getPassword().isEmpty()) {
            builder.withPassword(lettuceProperties.getPassword().toCharArray());
        }
        if (lettuceProperties.getTimeout() != null) {
            builder.withTimeout(lettuceProperties.getTimeout());
        }

        return builder.build();
    }

    /**
     * 单机redis 客户端
     *
     * @param clientResources client 资源
     * @param redisUri        redis 连接地址
     * @return {@link RedisClient}
     */
    @Bean(destroyMethod = "shutdown")
    @ConditionalOnProperty(name = "lettuce.host")
    public RedisClient singleRedisClient(ClientResources clientResources, @Qualifier("singleRedisUri") RedisURI redisUri) {
        return RedisClient.create(clientResources, redisUri);
    }

    // ====================== cluster ==================================================

    /**
     * 集群客户端 配置选项
     *
     * @param clientOptions 客户段配置
     * @return {@link ClusterClientOptions}
     */
    @Bean()
    @ConditionalOnProperty(name = "lettuce.cluster.nodes")
    public ClusterClientOptions clusterClientOptions(@Autowired ClientOptions clientOptions) {
        ClusterClientOptions.Builder builder = ClusterClientOptions.builder(clientOptions);

        // 集群客户端配置
        LettuceProperties.Cluster cluster = lettuceProperties.getCluster();
        if (null != cluster.getMaxRedirects()) {
            builder.maxRedirects(cluster.getMaxRedirects());
        }

        // 集群配置 -> 拓扑图刷新机制
        ClusterTopologyRefreshOptions.Builder topologyRefreshOptionsBuilder = ClusterTopologyRefreshOptions.builder();
        LettuceProperties.Cluster.Refresh clusterRefresh = cluster.getRefresh();
        topologyRefreshOptionsBuilder.dynamicRefreshSources(clusterRefresh.isDynamicRefreshSources());
        if (clusterRefresh.getPeriod() != null) {
            topologyRefreshOptionsBuilder.refreshPeriod(clusterRefresh.getPeriod());
        }
        if (clusterRefresh.isAdaptive()) {
            topologyRefreshOptionsBuilder.enableAllAdaptiveRefreshTriggers();
        }
        builder.topologyRefreshOptions(topologyRefreshOptionsBuilder.build());

        return builder.build();
    }

    /**
     * redis 集群客户端
     *
     * @param clientResources      client 资源
     * @param clusterClientOptions 集群客户端配置项
     * @return {@link RedisClusterClient}
     */
    @Bean(destroyMethod = "shutdown")
    @ConditionalOnProperty(name = "lettuce.cluster.nodes")
    public RedisClusterClient redisClusterClient(@Autowired ClientResources clientResources,
                                                 @Autowired ClusterClientOptions clusterClientOptions) {
        LettuceProperties.Cluster cluster = lettuceProperties.getCluster();

        // 集群url
        RedisClusterClient client = RedisClusterClient.create(clientResources, cluster.getNodes()
                .stream().map(s -> {
                    String[] split = s.split(":");
                    String host = split[0];
                    String port = split[1];
                    RedisURI.Builder builder = RedisURI.builder()
                            .withHost(host)
                            .withPort(Integer.parseInt(port))
                            .withDatabase(lettuceProperties.getDatabase());
                    if (!lettuceProperties.getPassword().isEmpty()) {
                        builder.withPassword(lettuceProperties.getPassword().toCharArray());
                    }
                    if (lettuceProperties.getTimeout() != null) {
                        builder.withTimeout(lettuceProperties.getTimeout());
                    }
                    return builder.build();
                }).collect(Collectors.toList()));

        // 集群客户端的配置项
        client.setOptions(clusterClientOptions);
        return client;
    }

    // ====================== pool ==================================================

    /**
     * 单机 连接池
     *
     * @param client     客户端
     * @param poolConfig 池配置
     * @return {@link GenericObjectPool<StatefulRedisConnection<String, String>>}
     */
    @ConditionalOnProperty(name = "lettuce.host")
    @Bean(destroyMethod = "close")
    public GenericObjectPool<StatefulRedisConnection<String, String>> connectionPool(@Autowired RedisClient client,
                                                                                     @Autowired GenericObjectPoolConfig<? extends StatefulConnection<String, String>> poolConfig) {
        return ConnectionPoolSupport.createGenericObjectPool(
                client::connect,
                ((GenericObjectPoolConfig<StatefulRedisConnection<String, String>>) poolConfig)
        );
    }

    /**
     * 集群 连接池
     *
     * @param clusterClient 集群客户端
     * @param poolConfig    池配置
     * @return {@link GenericObjectPool<StatefulRedisClusterConnection<String, String>>}
     */
    @ConditionalOnProperty(name = "lettuce.cluster.nodes")
    @Bean(destroyMethod = "close")
    public GenericObjectPool<StatefulRedisClusterConnection<String, String>> clusterConnectionPool(@Autowired RedisClusterClient clusterClient,
                                                                                                   @Autowired GenericObjectPoolConfig<? extends StatefulConnection<String, String>> poolConfig) {
        return ConnectionPoolSupport.createGenericObjectPool(
                clusterClient::connect,
                ((GenericObjectPoolConfig<StatefulRedisClusterConnection<String, String>>) poolConfig)
        );
    }

    /**
     * 池配置
     *
     * @param lettuceProperties lettuce 属性
     * @return {@link GenericObjectPoolConfig<? extends StatefulConnection<String, String>>}
     */
    @Bean
    public GenericObjectPoolConfig<? extends StatefulConnection<String, String>> poolConfig(LettuceProperties lettuceProperties) {
        LettuceProperties.Pool properties = lettuceProperties.getPool();

        GenericObjectPoolConfig<? extends StatefulConnection<String, String>> config = new GenericObjectPoolConfig<>();
        config.setMaxTotal(properties.getMaxActive());
        config.setMaxIdle(properties.getMaxIdle());
        config.setMinIdle(properties.getMinIdle());

        config.setJmxEnabled(false);  // 关掉监控 这个异常就不会抛出了~  MXBean already registered with name org.apache.commons.pool2:type=GenericObjectPool,name=pool

        if (properties.getTimeBetweenEvictionRuns() != null) {
            config.setTimeBetweenEvictionRunsMillis(properties.getTimeBetweenEvictionRuns().toMillis());
        }
        if (properties.getMaxWait() != null) {
            config.setMaxWaitMillis(properties.getMaxWait().toMillis());
        }
        return config;
    }
}