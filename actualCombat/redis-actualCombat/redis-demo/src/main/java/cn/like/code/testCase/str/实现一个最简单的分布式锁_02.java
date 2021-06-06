package cn.like.code.testCase.str;

import io.lettuce.core.SetArgs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

import static cn.like.code.testCase.Redis.reactive;

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
        count = lock(count, lock);

        // 第二次加锁
        count = lock(count, lock);
        // 删除lock
        reactive().del(lock).block();

        // 第三次加锁
        lock(count, lock);

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int lock(int count, String lock) {
        int finalCount = count;
      /*  cmd().set(lock, String.valueOf(++count), SetArgs.Builder.ex(100).nx())
                .subscribe(s -> {
                    log.info("[main] [第 " + finalCount + " 次 加锁 ]： {}", s);
                }, t -> {
                    System.out.println(t);
                    log.info("[main] [第 " + finalCount + "次 加锁 ]： {}", t.getMessage().getBytes(StandardCharsets.UTF_8));
                });*/

        String res = reactive().set(lock, String.valueOf(++count), SetArgs.Builder.ex(100).nx())
                .block();

        System.out.println("[main] [第  " + count + " 次 加锁 ]" + res);
        return count;
    }
}
