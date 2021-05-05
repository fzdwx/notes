package cn.like.redis.testCase;

import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.reactive.RedisReactiveCommands;

import java.time.Duration;

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
public class RedisClient {

    private RedisClient() {
    }

    private static final RedisReactiveCommands<String, String> CMD;

    static {
        RedisURI uri = new RedisURI("localhost", 6379, Duration.ZERO);
        StatefulRedisConnection<String, String> connect = io.lettuce.core.RedisClient.create(uri).connect();
        CMD = connect.reactive();
        CMD.select(0);
    }

    public static RedisReactiveCommands<String, String> redisCmd() {
        return CMD;
    }
}
