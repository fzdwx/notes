package cn.like.redis.testCase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

import static cn.like.redis.testCase.RedisClient.redisCmd;


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
 * @date 2021-05-05 13:43
 */
public class 普通的基于redis的缓存_01 {
    private static final Logger log = LoggerFactory.getLogger(普通的基于redis的缓存_01.class);

    public static void main(String[] args) {
        redisCmd().set("hello", "world").block();

        redisCmd().get("hello").subscribe(v -> {
            log.info("[main] [hello]: {}", v);
        });

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
