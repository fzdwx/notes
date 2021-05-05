package cn.like.redis.customizeLettuce;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.reactive.RedisReactiveCommands;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @date 2021-05-05 15:39
 */
@Component
public class LettuceClient {

    @Autowired
    RedisClient redisClient;

    @Bean
    public RedisReactiveCommands<String, String> reactiveCMD() {
        StatefulRedisConnection<String, String> connect = redisClient.connect();
        return connect.reactive();
    }
}
