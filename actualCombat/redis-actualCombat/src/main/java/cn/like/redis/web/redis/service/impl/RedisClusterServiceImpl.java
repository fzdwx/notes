package cn.like.redis.web.redis.service.impl;

import cn.like.redis.web.redis.LettucePoolConfig;
import cn.like.redis.web.redis.SyncCommandCallback;
import cn.like.redis.web.redis.service.RedisService;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
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
@Service
@ConditionalOnProperty(value = "spring.redis.lettuce.cluster.fire", havingValue = "true")
@Slf4j
public class RedisClusterServiceImpl implements RedisService {

    @Autowired
    private LettucePoolConfig lettucePoolConfig;

    @Override
    public <T> T executeSync(SyncCommandCallback<T> callback) {
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
}
