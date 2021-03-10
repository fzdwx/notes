package 再探JUC.自定义线程池;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-03-10 11:13
 */
@Slf4j
public class Test {

    public static void main(String[] args) {
        ThreadPool threadPool = new ThreadPool(new BlockingQueue<>(5), 3, 10, TimeUnit.MICROSECONDS, (q, t) -> {
            // 1.等待执行 - 死等
//            q.put(t);
            // 2.等待超时
            q.offer(t, 5,TimeUnit.MICROSECONDS);
            // 3.放弃
//            System.out.println("放弃执行"+t);
            // 4.让调用者抛出异常
//            throw new RuntimeException("线程池已满，执行失败"+t);
            // 5.调用者自己执行
//            ((Runnable) t).run();
        });
        for (int i = 0; i < 30; i++) {
            int val = i;
            threadPool.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                log.error("任务执行:{}",val);
            });

        }
    }
}
