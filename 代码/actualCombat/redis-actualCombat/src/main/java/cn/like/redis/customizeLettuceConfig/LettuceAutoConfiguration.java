package cn.like.redis.customizeLettuceConfig;

import cn.like.redis.customizeLettuceConfig.properties.*;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.codec.Utf8StringCodec;
import io.lettuce.core.masterslave.MasterSlave;
import io.lettuce.core.masterslave.StatefulRedisMasterSlaveConnection;
import io.lettuce.core.resource.ClientResources;
import io.lettuce.core.resource.DefaultClientResources;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
@ConditionalOnClass(name = "io.lettuce.core.RedisURI")
@EnableConfigurationProperties(value = LettuceProperties.class)
public class LettuceAutoConfiguration {
 
    private final LettuceProperties lettuceProperties;
 
    @Bean(destroyMethod = "shutdown")
    public ClientResources clientResources() {
        return DefaultClientResources.create();
    }
 
    @Bean
    @ConditionalOnProperty(name = "lettuce.single.host")
    public RedisURI singleRedisUri() {
        LettuceSingleProperties singleProperties = lettuceProperties.getSingle();
        return RedisURI.builder()
                .withHost(singleProperties.getHost())
                .withPort(singleProperties.getPort())
                // .withPassword(singleProperties.getPassword())
                .build();
    }
 
    @Bean(destroyMethod = "shutdown")
    @ConditionalOnProperty(name = "lettuce.single.host")
    public RedisClient singleRedisClient(ClientResources clientResources, @Qualifier("singleRedisUri") RedisURI redisUri) {
        return RedisClient.create(clientResources, redisUri);
    }
 
    @Bean(destroyMethod = "close")
    @ConditionalOnProperty(name = "lettuce.single.host")
    public StatefulRedisConnection<String, String> singleRedisConnection(@Qualifier("singleRedisClient") RedisClient singleRedisClient) {
        return singleRedisClient.connect();
    }
 
    @Bean
    @ConditionalOnProperty(name = "lettuce.replica.host")
    public RedisURI replicaRedisUri() {
        LettuceReplicaProperties replicaProperties = lettuceProperties.getReplica();
        return RedisURI.builder()
                .withHost(replicaProperties.getHost())
                .withPort(replicaProperties.getPort())
                .withPassword(replicaProperties.getPassword())
                .build();
    }
 
    @Bean(destroyMethod = "shutdown")
    @ConditionalOnProperty(name = "lettuce.replica.host")
    public RedisClient replicaRedisClient(ClientResources clientResources, @Qualifier("replicaRedisUri") RedisURI redisUri) {
        return RedisClient.create(clientResources, redisUri);
    }
 
    @Bean(destroyMethod = "close")
    @ConditionalOnProperty(name = "lettuce.replica.host")
    public StatefulRedisMasterSlaveConnection<String, String> replicaRedisConnection(@Qualifier("replicaRedisClient") RedisClient replicaRedisClient,
                                                                                     @Qualifier("replicaRedisUri") RedisURI redisUri) {
        return MasterSlave.connect(replicaRedisClient, new Utf8StringCodec(), redisUri);
    }
 
    @Bean
    @ConditionalOnProperty(name = "lettuce.sentinel.host")
    public RedisURI sentinelRedisUri() {
        LettuceSentinelProperties sentinelProperties = lettuceProperties.getSentinel();
        return RedisURI.builder()
                .withPassword(sentinelProperties.getPassword())
                .withSentinel(sentinelProperties.getHost(), sentinelProperties.getPort())
                .withSentinelMasterId(sentinelProperties.getMasterId())
                .build();
    }
 
    @Bean(destroyMethod = "shutdown")
    @ConditionalOnProperty(name = "lettuce.sentinel.host")
    public RedisClient sentinelRedisClient(ClientResources clientResources, @Qualifier("sentinelRedisUri") RedisURI redisUri) {
        return RedisClient.create(clientResources, redisUri);
    }
 
    @Bean(destroyMethod = "close")
    @ConditionalOnProperty(name = "lettuce.sentinel.host")
    public StatefulRedisMasterSlaveConnection<String, String> sentinelRedisConnection(@Qualifier("sentinelRedisClient") RedisClient sentinelRedisClient,
                                                                                      @Qualifier("sentinelRedisUri") RedisURI redisUri) {
        return MasterSlave.connect(sentinelRedisClient, new Utf8StringCodec(), redisUri);
    }
 
    @Bean
    @ConditionalOnProperty(name = "lettuce.cluster.host")
    public RedisURI clusterRedisUri() {
        LettuceClusterProperties clusterProperties = lettuceProperties.getCluster();
        return RedisURI.builder()
                .withHost(clusterProperties.getHost())
                .withPort(clusterProperties.getPort())
                // .withPassword(clusterProperties.getPassword())
                .build();
    }
 
    @Bean(destroyMethod = "shutdown")
    @ConditionalOnProperty(name = "lettuce.cluster.host")
    public RedisClusterClient redisClusterClient(ClientResources clientResources, @Qualifier("clusterRedisUri") RedisURI redisUri) {
        return RedisClusterClient.create(clientResources, redisUri);
    }
 
    @Bean(destroyMethod = "close")
    @ConditionalOnProperty(name = "lettuce.cluster")
    public StatefulRedisClusterConnection<String, String> clusterConnection(RedisClusterClient clusterClient) {
        return clusterClient.connect();
    }
}