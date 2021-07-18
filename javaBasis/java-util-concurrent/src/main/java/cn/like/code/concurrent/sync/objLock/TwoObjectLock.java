package cn.like.code.concurrent.sync.objLock;

import java.util.concurrent.TimeUnit;

/**
 * Description: 两个对象锁 <br>
 * <pre>
 *     两个代码块，分别使用不同的对象锁
 * </pre>
 *
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021-07-18 17:57:56
 * @see Runnable
 */
public class TwoObjectLock implements Runnable {

    public static TwoObjectLock instance = new TwoObjectLock();

    private static final Object LOCK1 = new Object();
    private static final Object LOCK2 = new Object();

    @Override
    public void run() {
        synchronized (LOCK1) {
            System.out.println("锁：lock-1-" + "我是线程" + Thread.currentThread().getName());

            try {
                // do something
                TimeUnit.SECONDS.sleep(3);
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("锁：lock-1-" + Thread.currentThread().getName() + " 结束");
        }

        synchronized (LOCK2) {
            System.out.println("锁：lock-2-" + "我是线程" + Thread.currentThread().getName());

            try {
                // do something
                TimeUnit.SECONDS.sleep(3);
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("锁：lock-2-" + Thread.currentThread().getName() + " 结束");
        }
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);
        t1.start();
        t2.start();
    }
}
