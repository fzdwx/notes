package cn.like.redis.controller;

import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.reactive.RedisReactiveCommands;
import io.lettuce.core.cluster.api.reactive.RedisAdvancedClusterReactiveCommands;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
 * @date 2021-05-05 15:35
 */
@RestController
public class HelloController {

    @Autowired(required = false)
    RedisAdvancedClusterReactiveCommands<String, String> reactiveClusterCMD;

    @Autowired
    RedisReactiveCommands<String, String> reactiveCMD;

    @Autowired
    GenericObjectPool<StatefulRedisConnection<String, String>> connectionPool;

    @GetMapping("/testConnPool")
    public String testConnPool() throws Exception {
        StatefulRedisConnection<String, String> conn = connectionPool.borrowObject();
        return conn.reactive().get("hello").block();
    }

    @GetMapping("/hello")
    public String hello() {
        return reactiveCMD.get("hello").block();
    }

    @GetMapping("/test")
    public String test() {
        return reactiveClusterCMD.clusterNodes().block();
    }

    /*@Override
    public <T> T get(String key, Class<T> clazz) {
        try (StatefulRedisClusterConnection connect = redisPool.borrowObject()){
            RedisAdvancedClusterCommands<String, String> redisCommands = connect.sync();
            String value = redisCommands.get(key);
            return JSONObject.parseObject(value, clazz);
        } catch (Exception e) {
            logger.warn("Redis get() failed! key=" + key, e);
            return null;
        }
    }*/
}
