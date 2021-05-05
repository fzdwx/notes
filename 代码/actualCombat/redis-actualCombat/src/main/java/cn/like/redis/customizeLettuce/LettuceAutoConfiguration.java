package cn.like.redis.customizeLettuce;

import cn.like.redis.customizeLettuce.properties.LettuceProperties;
import io.lettuce.core.RedisURI;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.resource.ClientResources;
import lombok.RequiredArgsConstructor;
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

    @Bean
    public ClientResources clientResources() {
        return ClientResources.builder()
                .build();
    }
 
  /*  @Bean(destroyMethod = "shutdown")
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
    }*/

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
}