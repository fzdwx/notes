package cn.like.redis.testCase;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.reactive.RedisReactiveCommands;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * .____    .__ __
 * |    |   |__|  | __ ____
 * |    |   |  |  |/ // __ \
 * |    |___|  |    <\  ___/
 * |_______ \__|__|_ \\___  >
 * \/       \/    \/
 * <p>
 * redis 连接程序
 *
 * @author like
 * @date 2021-05-05 13:46
 */
public class Redis {

    private Redis() {
    }

    public static RedisReactiveCommands<String, String> cmd() {
        RedisURI uri = RedisURI.builder()
                .withHost("localhost")
                .withPort(6379)
                .withTimeout(Duration.of(10, ChronoUnit.SECONDS))
                .build();
        RedisClient redisClient = RedisClient.create(uri);
        StatefulRedisConnection<String, String> connect = redisClient.connect();

        RedisReactiveCommands<String, String> cmd = connect.reactive();

        while (true) {
            if (cmd.isOpen()) {
                return cmd;
            }
        }
    }
}
