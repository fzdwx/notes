package 再探JUC.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-28 11:42
 */
@Slf4j
public class Test4 {

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            log.info("park....");
            // 进入park状态
            LockSupport.park();
            log.info("unpark···");
            log.info("打断状态:{}", Thread.currentThread().isInterrupted());

        }, "t1");
        t1.start();

        try {
            TimeUnit.SECONDS.sleep(1);
            // 打断状态
            t1.interrupt();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
