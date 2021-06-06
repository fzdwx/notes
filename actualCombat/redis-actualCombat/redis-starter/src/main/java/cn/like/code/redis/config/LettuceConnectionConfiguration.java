package cn.like.code.redis.config;

import cn.hutool.core.text.StrPool;
import cn.like.code.redis.properties.LettuceConfigProperties;
import cn.like.code.redis.service.RedisService;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;
import io.lettuce.core.cluster.RedisClusterClient;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * desc: redis 连接配置
 * details: 根据 {@link LettuceConfigProperties}里面的配置项进行配置
 * <pre>
 *     1. 单机
 *     2. 集群
 * </pre>
 *
 * @author: like
 * @since: 2021/6/6 11:39
 * @email: 980650920@qq.com
 */
@Configuration
@ConditionalOnClass(value = RedisService.class)
public class LettuceConnectionConfiguration {

    @Resource
    private LettuceConfigProperties properties;
    private final static Logger log = getLogger(LettuceConnectionConfiguration.class);
    private final static String REDIS_FIRE_CONFIG_PROPERTIES = "redis.fire";

    /**
     * Lettuce连接配置（Redis单机版实例）
     *
     * @return {@link RedisClient}
     */
    @Bean(name = "redisClient")
    @ConditionalOnProperty(value = REDIS_FIRE_CONFIG_PROPERTIES, havingValue = "false")
    public RedisClient redisClient() {
        log.info("[redis 客户端 初始化]-[ 单体 ]");

        final RedisURI.Builder builder = RedisURI.Builder.redis(properties.getHost(), properties.getPort());

        setting(builder);

        return RedisClient.create(builder.build());
    }

    // ===========  cluster ===========

    /**
     * redis 集群客户端
     *
     * @param clientOptions 客户的选择
     * @return {@link RedisClusterClient}
     */
    @Bean(name = "redisClient")
    @ConditionalOnProperty(value = REDIS_FIRE_CONFIG_PROPERTIES, havingValue = "true")
    public RedisClusterClient redisClusterClient(ClusterClientOptions clientOptions) {
        log.info("[redis 客户端 初始化]-[ 集群 ]");

        final List<String> nodes = properties.getCluster().getNodes();
        final String[] strings = nodes.remove(0).split(StrPool.COLON);
        final RedisURI.Builder builder = RedisURI.Builder.redis(strings[0], Integer.parseInt(strings[1]));

        final List<RedisURI> uris =  nodes.stream().map(node->{
            final String[] hostPort = node.split(StrPool.COLON);
            return RedisURI.create(hostPort[0], Integer.parseInt(hostPort[1]));
        }).collect(Collectors.toList());

        setting(builder);
        uris.add(builder.build());

        RedisClusterClient redisClusterClient = RedisClusterClient.create(uris);

        redisClusterClient.setOptions(clientOptions);

        return redisClusterClient;
    }

    /**
     * desc: 集群客户端配置
     * details: 最大重定向,自动重新连接,拓扑图刷新规则......
     *
     * @param clusterTopologyRefreshOptions 集群拓扑刷新选项
     * @return {@link ClusterClientOptions}
     */
    @Bean
    public ClusterClientOptions clientOptions(ClusterTopologyRefreshOptions clusterTopologyRefreshOptions) {
        return ClusterClientOptions.builder()
                .maxRedirects(properties.getCluster().getMaxRedirects())
                .autoReconnect(properties.getCluster().isAutoReconnect())
                .topologyRefreshOptions(clusterTopologyRefreshOptions)
                .build();
    }

    /**
     * desc:集群拓扑刷新选项
     *
     * @return {@link ClusterTopologyRefreshOptions}
     */
    @Bean
    public ClusterTopologyRefreshOptions clusterTopologyRefreshOptions() {
        return ClusterTopologyRefreshOptions.builder()
                .enableAdaptiveRefreshTrigger(
                        ClusterTopologyRefreshOptions.RefreshTrigger.MOVED_REDIRECT,
                        ClusterTopologyRefreshOptions.RefreshTrigger.PERSISTENT_RECONNECTS)
                .adaptiveRefreshTriggersTimeout(Duration.of(30, ChronoUnit.SECONDS))
                .build();
    }

    /**
     * desc: redis通用设置
     * details: username,password,auth...
     *
     * @param builder 构建器
     */
    private void setting(RedisURI.Builder builder) {
        // auth
        if (StringUtils.isNoneBlank(properties.getPassword())) {
            if (StringUtils.isNoneBlank(properties.getUsername())) {
                builder.withAuthentication(properties.getUsername(), properties.getPassword().toCharArray());
            } else {
                builder.withPassword(properties.getPassword().toCharArray());
            }
        }
        // client name
        if (StringUtils.isNoneBlank(properties.getClientName())) {
            builder.withClientName(properties.getClientName());
        }
        // timeout
        if (properties.getTimeout() != null) {
            builder.withTimeout(properties.getTimeout());
        }
        // database
        builder.withDatabase(properties.getDatabase());
        // ssl
        if (properties.getSsl() != null) {
            builder.withSsl(properties.getSsl());
        }
    }
}
