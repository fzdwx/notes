package 再探JUC.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-28 11:54
 */
@Slf4j
public class Test5 {

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            log.info("t1 开始运行");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("t1 运行结束");
        }, "t1");
        // 设置为守护线程
        t1.setDaemon(true);
        t1.start();

        log.info("main 开始运行");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("main 运行结束");
    }
}
