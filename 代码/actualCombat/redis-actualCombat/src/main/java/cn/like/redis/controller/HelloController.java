package cn.like.redis.controller;

import cn.hutool.json.JSONUtil;
import cn.like.redis.entity.Person;
import io.lettuce.core.api.reactive.RedisReactiveCommands;
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
 * @date 2021-05-05 15:35
 */
@RestController
public class HelloController {

    @Autowired
    RedisReactiveCommands<String, String> reactiveCMD;

    @GetMapping("/hello")
    public String hello() {
        Person person = new Person();
        person.setName("like");
        person.setAge(17);
        reactiveCMD.set("testweb", JSONUtil.toJsonStr(person));
        Mono<String> res = reactiveCMD.get("testweb");
        return JSONUtil.toBean(res.block(), Person.class).toString();
    }
}
