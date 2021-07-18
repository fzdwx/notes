package cn.like.code.concurrent.sync.classLock;

import java.util.concurrent.TimeUnit;

/**
 * Description: 指定类锁 <br>
 *
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021-07-18 18:21:41
 */
public class AssignClassLock implements Runnable {

    private static final AssignClassLock instance1 = new AssignClassLock();
    private static final AssignClassLock instance2 = new AssignClassLock();

    @Override
    public void run() {
        synchronized (AssignClassLock.class) {
            System.out.println("我是线程" + Thread.currentThread().getName());

            try {
                // do something
                TimeUnit.SECONDS.sleep(3);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " 结束");
        }
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(instance1);
        Thread t2 = new Thread(instance2);
        t1.start();
        t2.start();
    }
}
