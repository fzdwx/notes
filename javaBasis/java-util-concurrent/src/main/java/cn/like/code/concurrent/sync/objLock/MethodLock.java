package cn.like.code.concurrent.sync.objLock;

import java.util.concurrent.TimeUnit;

/**
 * Description: 方法锁 <br>
 *
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021-07-18 18:02:58
 */
public class MethodLock implements Runnable {

    private static MethodLock instance = new MethodLock();

    @Override
    public void run() {
        method();
    }

    // 方法锁
    private synchronized void method() {
        System.out.println("我是线程" + Thread.currentThread().getName());

        try {
            // do something
            TimeUnit.SECONDS.sleep(3);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " 结束");
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);
        t1.start();
        t2.start();
    }
}
