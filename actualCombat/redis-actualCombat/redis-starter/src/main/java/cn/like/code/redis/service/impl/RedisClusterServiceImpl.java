package cn.like.code.redis.service.impl;


import cn.like.code.redis.config.LettucePoolConfiguration;
import cn.like.code.redis.service.RedisService;
import cn.like.code.redis.service.support.ASyncCommandCallback;
import cn.like.code.redis.service.support.ReactiveCommandCallback;
import cn.like.code.redis.service.support.SyncCommandCallback;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.cluster.api.async.RedisAdvancedClusterAsyncCommands;
import io.lettuce.core.cluster.api.reactive.RedisAdvancedClusterReactiveCommands;
import io.lettuce.core.cluster.api.sync.RedisAdvancedClusterCommands;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * redis 集群实现
 *
 * @author like
 * @date 2021/6/1 9:39
 */
@Slf4j
@Service
@ConditionalOnProperty(value = "redis.fire", havingValue = "true")
public class RedisClusterServiceImpl implements RedisService {

    @Autowired
    private LettucePoolConfiguration lettucePoolConfig;

    @Override
    public <T> T sync(SyncCommandCallback<T> callback) {
        try (StatefulRedisClusterConnection<String, String> connection =
                     lettucePoolConfig.getRedisClusterConnectionPool().borrowObject()) {
            //开启自动提交，如果false，命令会被缓冲，调用flushCommand()方法发出
            connection.setAutoFlushCommands(true);
            final RedisAdvancedClusterCommands<String, String> sync = connection.sync();

            return callback.apply(sync);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T async(ASyncCommandCallback<T> callback) {
        try (StatefulRedisClusterConnection<String, String> connection =
                     lettucePoolConfig.getRedisClusterConnectionPool().borrowObject()) {
            connection.setAutoFlushCommands(true);
            final RedisAdvancedClusterAsyncCommands<String, String> async = connection.async();

            return callback.apply(async);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T reactive(ReactiveCommandCallback<T> callback) {
        try (StatefulRedisClusterConnection<String, String> connection =
                     lettucePoolConfig.getRedisClusterConnectionPool().borrowObject()) {
            connection.setAutoFlushCommands(true);

            final RedisAdvancedClusterReactiveCommands<String, String> reactive = connection.reactive();
            return callback.apply(reactive);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
