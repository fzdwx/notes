package cn.like.redis.web.controller;

import cn.like.redis.customizeLettuce.Lettuce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

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
@RestController
public class HelloController {

    @Autowired
    Lettuce lettuce;

    @GetMapping("/testConnPool")
    public Mono<String> testConnPool() throws Exception {
        return lettuce.getConn().reactive().get("hello");
    }
}
