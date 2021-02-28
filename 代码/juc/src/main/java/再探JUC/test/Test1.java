package 再探JUC.test;

import lombok.extern.slf4j.Slf4j;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-28 9:13
 */
@Slf4j(topic = "c.Test1")
public class Test1 {

    public static void main(String[] args) {
        Thread t1 =
                new Thread(
                        () -> {
                            //
                            log.info("running");
                        },
                        "t1");
        t1.start();

        log.info("running");
    }
}
