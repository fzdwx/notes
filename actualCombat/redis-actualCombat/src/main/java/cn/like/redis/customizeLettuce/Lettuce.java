package cn.like.redis.customizeLettuce;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.reactive.RedisReactiveCommands;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.reactive.RedisAdvancedClusterReactiveCommands;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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
 */
@Component
public class Lettuce {

    @Autowired
    GenericObjectPool<StatefulRedisConnection<String, String>> connectionPool;
    // @Autowired
    // GenericObjectPool<StatefulRedisClusterConnection<String, String>> clusterConnPool;

    @Bean
    @ConditionalOnProperty(name = "lettuce.cluster.nodes")
    public RedisAdvancedClusterReactiveCommands<String, String> reactiveClusterCMD(@Autowired RedisClusterClient redisClusterClient) {
        return redisClusterClient.connect().reactive();
    }

    @Bean
    @ConditionalOnProperty(name = "lettuce.host")
    public RedisReactiveCommands<String, String> reactiveCMD(@Autowired RedisClient redisClient) {
        return redisClient.connect().reactive();
    }

    public String get(String key) {
        try (StatefulRedisConnection<String, String> conn = connectionPool.borrowObject()) {
            return conn.sync().get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public StatefulRedisConnection<String, String> getConn() throws Exception {
       /* try (StatefulRedisConnection<String, String> stringStringStatefulRedisConnection = connectionPool.borrowObject()) {
            return stringStringStatefulRedisConnection;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
        */
        return connectionPool.borrowObject();
    }
}
