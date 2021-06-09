package cn.like.code.concurrent.case_1;

/**
 * @author like
 * @date 2021/6/9 15:30
 */
public class ThreadLocalExample_1 {

    public static void main(String[] args) throws InterruptedException {
        final var th1 = new ThreadLocal<Integer>();
        final var th2 = new ThreadLocal<Integer>();

        new Thread(() -> {
            th1.set(1);
            th2.set(1);
        }, "t1").start();

        new Thread(() -> {
            th1.set(2);
            th2.set(2);
        }, "t2").start();
    }
}
