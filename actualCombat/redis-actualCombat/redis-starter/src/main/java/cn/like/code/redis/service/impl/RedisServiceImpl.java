package cn.like.code.redis.service.impl;

import cn.like.code.redis.config.LettucePoolConfiguration;
import cn.like.code.redis.service.RedisService;
import cn.like.code.redis.service.support.ASyncCommandCallback;
import cn.like.code.redis.service.support.ReactiveCommandCallback;
import cn.like.code.redis.service.support.SyncCommandCallback;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.reactive.RedisReactiveCommands;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * redis 单机命令
 *
 * @author like
 * @date 2021/6/1 9:36
 */
@Slf4j
@Service
@ConditionalOnProperty(value = "redis.fire", havingValue = "false")
public class RedisServiceImpl implements RedisService {

    @Autowired
    private LettucePoolConfiguration lettucePoolConfig;

    @Override
    public <T> T sync(SyncCommandCallback<T> callback) {
        try (StatefulRedisConnection<String, String> connection =
                     lettucePoolConfig.getRedisConnectionPool().borrowObject()) {
            // 开启自动提交，如果false，命令会被缓冲，调用flushCommand()方法发出
            connection.setAutoFlushCommands(true);
            final RedisCommands<String, String> commands = connection.sync();

            return callback.apply(commands);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T async(ASyncCommandCallback<T> callback) {
        try (StatefulRedisConnection<String, String> connection =
                     lettucePoolConfig.getRedisConnectionPool().borrowObject()) {
            connection.setAutoFlushCommands(true);
            final RedisAsyncCommands<String, String> async = connection.async();

            return callback.apply(async);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T reactive(ReactiveCommandCallback<T> callback) {
        try (StatefulRedisConnection<String, String> connection =
                     lettucePoolConfig.getRedisConnectionPool().borrowObject()) {
            connection.setAutoFlushCommands(true);
            final RedisReactiveCommands<String, String> reactive = connection.reactive();

            return callback.apply(reactive);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
