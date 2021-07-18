package cn.like.code.concurrent.sync.classLock;

import java.util.concurrent.TimeUnit;

/**
 * Description: 类锁-静态方法锁 <br>
 *
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021-07-18 18:14:05
 */
public class StaticMethodLock implements Runnable {

    private static final StaticMethodLock instance1 = new StaticMethodLock();
    private static final StaticMethodLock instance2 = new StaticMethodLock();

    public static void method() {
        System.out.println("我是线程" + Thread.currentThread().getName());

        try {
            // do something
            TimeUnit.SECONDS.sleep(3);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " 结束");
    }

    @Override
    public void run() {
        method();
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(instance1);
        Thread t2 = new Thread(instance2);
        t1.start();
        t2.start();
    }
}
