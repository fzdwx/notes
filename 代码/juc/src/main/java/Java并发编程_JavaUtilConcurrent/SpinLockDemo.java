package Java并发编程_JavaUtilConcurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 *  自旋锁案例
 * @author likeLove
 * @since 2020-09-24  10:26
 */
public class SpinLockDemo {

    private final static SpinLockDemo sld = new SpinLockDemo();

    AtomicReference<Thread> reference = new AtomicReference<>();

    public void myLock() {
        Thread thread = Thread.currentThread();
        System.out.println("come in " + thread.getName());
        while (!reference.compareAndSet(null, thread)) {

        }
    }
    public void myUnLock() {
        Thread thread = Thread.currentThread();
        while (reference.compareAndSet(thread, null)) {

        }
        System.out.println("un lock " + thread.getName());
    }
    public static void main(String[] args) {
        new Thread(() -> {
            sld.myLock();
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sld.myUnLock();
        },"A").start();
        new Thread(() -> {
            sld.myLock();
            sld.myUnLock();
        },"B").start();
    }
}
