package 再探JUC.test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-03-09 8:47
 */
public class Test8 {
    private static AtomicInteger i = new AtomicInteger();

    public static void main(String[] args) {
        new Thread(() -> {
            i.set(10);
            i.incrementAndGet();
            i.compareAndSet(11, 20);
            System.out.println(i.get());
        }, "t1").start();

        new Thread(() -> {
            i.set(100);
        }, "t2").start();

        LongAdder adder = new LongAdder();
        adder.add(1);
    }
}
