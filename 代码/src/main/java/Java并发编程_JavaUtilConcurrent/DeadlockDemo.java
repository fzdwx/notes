package Java并发编程_JavaUtilConcurrent;

import java.util.concurrent.TimeUnit;

/**
 * @author likeLove
 * @since 2020-09-26  14:39
 * 指两个或两个以上的进程在执行过程中，因争夺资源而造成的一种相互等待的现象。
 * 若无外力干涉那它们都将无法推进下去，如果系统资源充足，进程的资源请求都能得到满足，
 * 死锁出现的可能性就很低，否则就会因为争夺有限的资源陷入死锁。
 * jps -l
 * jstack xxx
 */
public class DeadlockDemo {
    public static void main(String[] args) {
        String a = "lock A";
        String b = "lock B";
        new Thread(new MyDeadData(a, b), "线程A").start();
        new Thread(new MyDeadData(b, a), "线程B").start();
    }
}
/**
 * 持有自己的，想要别人的
 */
class MyDeadData implements Runnable {
    private String lockA;
    private String lockB;
    public MyDeadData(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }
    @Override
    public void run() {
        //持有一个锁
        synchronized (lockA) {
            System.out.println(Thread.currentThread().getName() + "\t 持有：" + lockA + "\t 尝试获取" + lockB);
            try {TimeUnit.SECONDS.sleep(2); } catch (InterruptedException e) {e.printStackTrace();}
            //想要获取另一把锁
            synchronized (lockB) {
                System.out.println(Thread.currentThread().getName() + "\t 持有：" + lockB + "\t 尝试获取" + lockA);
            }
        }
    }
}

