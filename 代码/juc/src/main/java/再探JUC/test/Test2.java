package 再探JUC.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-28 11:06
 */
@Slf4j(topic = "c.test2")
public class Test2 {

    public static void main(String[] args) {
        Thread h1 = new Thread(() -> {
            log.info("h1 start");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("h1 over");
        }, "h1");
        h1.start();
        try {
            h1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("main running");

    }
}
