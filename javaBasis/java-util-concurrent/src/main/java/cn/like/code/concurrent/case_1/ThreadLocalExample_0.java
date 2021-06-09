package cn.like.code.concurrent.case_1;

import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * desc: thread local 无同步方案
 *
 * @author like
 * @date 2021/6/9 15:25
 */
@Slf4j
public class ThreadLocalExample_0 {

    public static void main(String[] args) {
        final var threadLocal = new ThreadLocal<Integer>();

        new Thread(() -> {
            threadLocal.set(1);

            // sleep
            ThreadUtil.sleep(1000);

            log.info("[t1][ thread local val] : {}",threadLocal.get());

        }, "t1").start();

        new Thread(() -> {
            threadLocal.set(2);

            log.info("[t2][ thread local val] : {}",threadLocal.get());

        }, "t2").start();
    }
}
