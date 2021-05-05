package cn.like.redis.testCase;

import io.lettuce.core.SetArgs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

import static cn.like.redis.testCase.Redis.cmd;

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
 * @date 2021-05-05 14:21
 */
public class 实现一个最简单的分布式锁_02 {

    private final static Logger log = LoggerFactory.getLogger(实现一个最简单的分布式锁_02.class);

    public static void main(String[] args) {
        int count = 0;
        String lock = "lock_test";

        // 第一次加锁
        cmd().set(lock, String.valueOf(++count), SetArgs.Builder.ex(100).nx())
                .subscribe(res -> {
                    log.info("[main] [第 1 次 加锁 ]： {}", res);
                });

        // 第二次加锁
        cmd().set(lock , String.valueOf(++count), SetArgs.Builder.ex(100).nx())
                .subscribe(res -> {
                    log.info("[main] [第 2 次 加锁 ]： {}", res);
                });

        // 删除lock
        cmd().del(lock + count).block();

        // 第三次加锁
        cmd().set(lock, String.valueOf(++count), SetArgs.Builder.ex(100).nx())
                .subscribe(res -> {
                    log.info("[main] [第 3 次 加锁 ]： {}", res);
                });

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
