package cn.like.redis.customizeLettuce;

import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.reactive.RedisAdvancedClusterReactiveCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * .____    .__ __
 * |    |   |__|  | __ ____
 * |    |   |  |  |/ // __ \
 * |    |___|  |    <\  ___/
 * |_______ \__|__|_ \\___  >
 * \/       \/    \/
 * <p>
 * desc
 *
 * @author like
 */
@Component
public class Lettuce {

    @Bean
    @ConditionalOnProperty(name = "lettuce.cluster.nodes")
    public RedisAdvancedClusterReactiveCommands<String, String> reactiveClusterCMD(@Autowired RedisClusterClient redisClusterClient) {
        return redisClusterClient.connect().reactive();
    }
}
