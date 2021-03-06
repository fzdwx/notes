package 再探JUC.pattern.同步模式之保护性暂停;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author like
 * @email 980650920@qq.com
 * @Description: 同步模式之保护性暂停
 * @since 2021-03-06 10:35
 */
@Slf4j
public class Test1 {
    public static void main(String[] args) {
        GuardedObject guarded = new GuardedObject();
        new Thread(() -> {
            // 等待结果
            log.info("等待结果");
            Object o = guarded.get(1);
            log.info("结果是:{}", o);
        }, "t1").start();
        new Thread(() -> {
            try {
                log.info("执行下载");
                TimeUnit.SECONDS.sleep(3);
                // 下载完成
                log.info("下载完成");
                guarded.complete(new Person("like", 18));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "t2").start();
    }
}



