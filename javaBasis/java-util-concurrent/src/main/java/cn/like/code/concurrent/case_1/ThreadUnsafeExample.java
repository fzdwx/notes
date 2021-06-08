package cn.like.code.concurrent.case_1;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

/**
 * desc: 多线程问题引入
 *
 * @author like
 * @date 2021/6/7 16:21
 */
public class ThreadUnsafeExample {

    private int cnt = 0;

    public void add() {
        this.cnt++;
    }

    public int cnt() {
        return cnt;
    }

    public void cnt(int cnt) {
        this.cnt = cnt;
    }

    public static void main(String[] args) throws InterruptedException {
        var example = new ThreadUnsafeExample();
        final var threadSize = 10000; // fixed thread count

        var pool = Executors.newCachedThreadPool();
        while (true) {
            var countDownLatch = new CountDownLatch(threadSize);

            for (int i = 0; i < threadSize; i++) {
                pool.execute(() -> {
                    example.add();
                    countDownLatch.countDown();
                });
            }
            countDownLatch.await();
            if (threadSize == example.cnt()) break;  // 相等就返回

            example.cnt(0); // reset
        }
        pool.shutdown();

        System.out.println(example.cnt()); // 永远小于 10000
    }
}
