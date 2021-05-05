package cn.like.redis.testCase;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.reactive.RedisReactiveCommands;
import org.slf4j.Logger;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static org.slf4j.LoggerFactory.getLogger;

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

    private final static Logger log = getLogger(Redis.class);
    private static final StatefulRedisConnection<String, String> connect;
    private static final RedisReactiveCommands<String, String> cmd;

    private Redis() {
    }

    static {

        RedisURI uri = RedisURI.builder()
                .withHost("localhost")
                .withPort(6379)
                .withTimeout(Duration.of(10, ChronoUnit.SECONDS))
                .build();
        RedisClient redisClient = RedisClient.create(uri);
        connect = redisClient.connect();
        cmd = connect.reactive();
    }

    public static RedisReactiveCommands<String, String> cmd() {
      /*  GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxIdle(10);
        poolConfig.setMaxTotal(10);
        GenericObjectPool<StatefulRedisConnection<String, String>> pool
                = ConnectionPoolSupport.createGenericObjectPool(redisClient::connect, poolConfig);

        pool.close();
        redisClient.shutdown();*/

        while (true) {
            if (cmd.isOpen()) {
                return cmd;
            }
        }
    }
}
