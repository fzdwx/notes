package 再探JUC.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-03-09 10:34
 */
@Slf4j
public class Test9 {

    public static void main(String[] args) {
        CASLock lock = new CASLock();
        new Thread(() -> {
            log.info("start");
            lock.lock();
            try {
                log.warn("加锁成功");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }finally {
                log.warn("解锁");
                lock.unlock();
            }
            log.info("end");
        }, "t1").start();

        new Thread(() -> {
            log.info("start");
            lock.lock();
            try {
                log.warn("加锁成功");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }finally {
                log.warn("解锁");
                lock.unlock();
            }
            log.info("end");
        }, "t2").start();
    }
}

@Slf4j
class CASLock {
    private AtomicInteger flag = new AtomicInteger(0);


    public void lock() {
        while (true) {
            if (flag.compareAndSet(0, 1)) {
                break;
            }
        }
    }

    public void unlock() {
        flag.set(0);
    }
}