package cn.like.redis.testCase;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.reactive.RedisReactiveCommands;
import io.lettuce.core.resource.ClientResources;
import io.lettuce.core.resource.DefaultClientResources;

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

    private static final RedisReactiveCommands<String, String> CMD;

    static {

        ClientResources resources = DefaultClientResources.builder()

                .build();
        RedisURI uri = RedisURI.builder()
                .withHost("localhost")
                .withPort(6379)
                .withTimeout(Duration.of(10, ChronoUnit.SECONDS))
                .build();
        RedisClient redisClient = RedisClient.create(uri);
        StatefulRedisConnection<String, String> connect = redisClient.connect();
        CMD = connect.reactive();
    }

    public static RedisReactiveCommands<String, String> cmd() {
        while (true) {
            if (CMD.isOpen()) {
                return CMD;
            }
        }
    }
}
