package cn.like.code.concurrent.sync.objLock;

import java.util.concurrent.TimeUnit;

/**
 * Description: 对象锁 <br>
 * <pre>
 *     1.启动两个线程,使用同一把锁。
 *     2.结果是先后执行，而不是同时执行
 * </pre>
 *
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021-07-18 15:22:13
 * @see Runnable
 */
public class ObjectLock implements Runnable {

    private final static ObjectLock INSTANCE = new ObjectLock();

    public static ObjectLock getInstance() {
        return INSTANCE;
    }

    private ObjectLock() { }

    @Override
    public void run() {
        // 对象锁，锁主的是当前对象
        synchronized (this) {
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
        new Thread(getInstance()).start();
        new Thread(getInstance()).start();
    }
}
