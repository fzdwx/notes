package 再探JUC.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-03-07 11:41
 */
@Slf4j
public class Test7 {
    static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        Condition c1 = lock.newCondition();
        new Thread(() -> {
            lock.lock();
            if (lock.isLocked()) {
                try {
                    c1.await();
                    log.info("1");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            lock.unlock();
        }, "1").start();


        new Thread(() -> {
           while (lock.tryLock()){
               if (lock.isLocked()) {
                   log.info("2");
                   c1.signal();
                   lock.unlock();
                   break;
               }
           }

        }, "2").start();
    }
}
