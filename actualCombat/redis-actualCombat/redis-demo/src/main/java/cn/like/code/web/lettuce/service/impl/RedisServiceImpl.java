package cn.like.code.web.lettuce.service.impl;

import cn.like.code.web.lettuce.LettucePoolConfig;
import cn.like.code.web.lettuce.SyncCommandCallback;
import cn.like.code.web.lettuce.service.RedisService;
import io.lettuce.core.api.StatefulRedisConnection;
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
@Service
@ConditionalOnProperty(value = "spring.redis.lettuce.cluster.fire", havingValue = "false")
@Slf4j
public class RedisServiceImpl implements RedisService {

    @Autowired
    private LettucePoolConfig lettucePoolConfig;

    @Override
    public <T> T executeSync(SyncCommandCallback<T> callback) {
        try (StatefulRedisConnection<String, String> connection =
                     lettucePoolConfig.getRedisConnectionPool().borrowObject()) {
            // 开启自动提交，如果false，命令会被缓冲，调用flushCommand()方法发出
            connection.setAutoFlushCommands(true);
            RedisCommands<String, String> commands = connection.sync();
            return callback.apply(commands);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
