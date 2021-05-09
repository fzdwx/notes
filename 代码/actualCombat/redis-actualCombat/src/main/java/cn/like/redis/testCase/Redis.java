package cn.like.redis.testCase;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.reactive.RedisReactiveCommands;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.resource.DefaultClientResources;
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
 */
public class Redis {

    private final static Logger log = getLogger(Redis.class);
    private static final StatefulRedisConnection<String, String> connect;
    private static final RedisReactiveCommands<String, String> cmd;
    public static final RedisCommands<String, String> sync;
    public static final RedisAsyncCommands<String, String> async;

    private Redis() {
    }

    static {

        RedisURI uri = RedisURI.builder()
                .withHost("localhost")
                .withPort(6379)
                .withTimeout(Duration.of(10, ChronoUnit.SECONDS))
                .build();
        DefaultClientResources.Builder builder = DefaultClientResources.builder();
        RedisClient redisClient = RedisClient.create(builder.build(), uri);
        connect = redisClient.connect();
        cmd = connect.reactive();

        sync = connect.sync();

        async = connect.async();
    }

    public static RedisAsyncCommands<String, String> async() {
        while (true) {
            if (sync.isOpen()) {
                return async;
            }
        }
    }

    public static RedisCommands<String, String> sync() {
        while (true) {
            if (sync.isOpen()) {
                return sync;
            }
        }
    }

    public static RedisReactiveCommands<String, String> reactive() {
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
