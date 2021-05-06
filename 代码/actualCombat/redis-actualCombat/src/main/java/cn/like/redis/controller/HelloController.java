package cn.like.redis.controller;

import io.lettuce.core.api.reactive.RedisReactiveCommands;
import io.lettuce.core.cluster.api.reactive.RedisAdvancedClusterReactiveCommands;
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

    @GetMapping("/hello")
    public String hello() {
        return reactiveCMD.get("hello").block();
    }

    @GetMapping("/test")
    public String test() {
        return reactiveClusterCMD.clusterNodes().block();
    }
}
