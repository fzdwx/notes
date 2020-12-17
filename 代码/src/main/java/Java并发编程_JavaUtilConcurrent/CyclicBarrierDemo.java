package Java并发编程_JavaUtilConcurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author likeLove
 * @since 2020-09-24  18:23
 */

public class CyclicBarrierDemo {
    static CyclicBarrier cb = new CyclicBarrier(7, () -> {
        System.out.println("集齐7颗龙珠，召唤神龙");
    });
    public static void main(String[] args) {
        for (int i = 0; i < 7; i++) {
            int finalI = i;
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t 收集到第" + finalI + "龙珠");
                try {
                    //阻塞
                    cb.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            },String.valueOf(i) ).start();
        }
    }
}
