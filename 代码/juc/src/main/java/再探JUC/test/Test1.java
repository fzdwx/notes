package 再探JUC.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-28 9:13
 */
@Slf4j(topic = "c.Test1")
public class Test1 {

    public static void main(String[] args) {
        Thread t1 = new Thread(
                () -> {
                    log.info("hello,t1");
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, "t1");

        t1.start();

        try {
            log.info("t1 state:{}", t1.getState());
            TimeUnit.SECONDS.sleep(1);
            log.info("t1 state:{}", t1.getState());
            TimeUnit.SECONDS.sleep(3);
            log.info("t1 state:{}", t1.getState());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
