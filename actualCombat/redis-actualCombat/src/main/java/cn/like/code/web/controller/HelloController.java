package cn.like.code.web.controller;

import cn.like.code.web.lettuce.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
 */
@RestController
public class HelloController {

    @Autowired
    private RedisService redisService;

    @GetMapping("/get/{key}")
    public String get(@PathVariable String key) throws Exception {
        return redisService.get(key);
    }

    @GetMapping("/set/{key}/{value}")
    public String set(@PathVariable String key, @PathVariable String value) {
        return redisService.set(key, value);
    }

    @GetMapping("/hello")
    public String hello() {
        final Long res = redisService.executeSync(commands -> {
            final Boolean hSet = commands.hset("13", "123", "1");
            Long del = 0L;
            if (hSet) {
                del = commands.del("13");
            }
            return del;
        });

        return res.toString();
    }
}
