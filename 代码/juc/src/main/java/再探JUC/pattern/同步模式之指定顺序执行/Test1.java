package 再探JUC.pattern.同步模式之指定顺序执行;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-03-07 12:19
 */
@Slf4j
public class Test1 {

    public void m1() {
        Thread t1 = new Thread(() -> {
            LockSupport.park();
            log.info("1");
        }, "1");

        Thread t2 = new Thread(() -> {
            log.info("2");
            LockSupport.unpark(t1);
        }, "2");

        t1.start();
        t2.start();
    }
}
