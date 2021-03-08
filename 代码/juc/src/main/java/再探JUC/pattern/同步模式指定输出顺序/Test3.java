package 再探JUC.pattern.同步模式指定输出顺序;

import java.util.concurrent.locks.LockSupport;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-03-08 9:42
 */
public class Test3 {

    static Thread a;
    static Thread b;
    static Thread c;

    public static void main(String[] args) {
        ParkUnPark pu = new ParkUnPark(5);
        a = new Thread(() -> {
            pu.print(Thread.currentThread().getName(), b);
        }, "a");
        b = new Thread(() -> {
            pu.print(Thread.currentThread().getName(), c);
        }, "b");
        c = new Thread(() -> {
            pu.print(Thread.currentThread().getName(), a);
        }, "c");

        a.start();
        b.start();
        c.start();

        LockSupport.unpark(a);
    }
}

class ParkUnPark {

    private int loop;

    public ParkUnPark(int loop) {
        this.loop = loop;
    }

    public void print(String val, Thread next) {
        for (int i = 0; i < loop; i++) {
            LockSupport.park();
            System.out.print(val);
            LockSupport.unpark(next);
        }
    }
}
