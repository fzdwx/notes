package Java并发编程_JavaUtilConcurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author likeLove
 * @since 2020-09-25  11:23
 * synchronized 和 lock的区别
 * <ul>
 *    任务要求：
 *    <li> A运行五次，b运行10次，c运行15次</li>
 *    <li> 循环10次，顺序不变</li>
 *    <li>精确唤醒</li>
 * </ul>
 * <p>
 */
public class SyncAndLockDemo {
    public static void main(String[] args) {
        ShareData data = new ShareData();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                data.print5();
            }
        }, "A").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                data.print10();
            }
        }, "B").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                data.print15();
            }
        }, "C").start();
    }
}

class ShareData {
    private int number = 1; //A=1,B=2,C=3
    private Lock lock = new ReentrantLock();
    private Condition a = lock.newCondition();//解锁A
    private Condition b = lock.newCondition();//解锁b
    private Condition c = lock.newCondition();//解锁c
    public void print5() {
        lock.lock();
        try {
            //1.判断：如果不是1就等待
            while (number != 1) { a.await(); }
            //2.干活：输出5次
            for (int i = 1; i <= 5; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            //3.通知：b线程
            number = 2;
            //唤醒b
            b.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    public void print10() {
        lock.lock();
        try {
            //1.判断：如果不是2就等待
            while (number != 2) { b.await(); }
            //2.干活：输出10次
            for (int i = 1; i <= 10; i++) { System.out.println(Thread.currentThread().getName() + "\t" + i); }
            //3.通知：c线程
            number = 3;
            //唤醒c
            c.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    public void print15() {
        lock.lock();
        try {
            //1.判断：如果不是3就等待
            while (number != 3) { c.await(); }
            //2.干活：输出5次
            for (int i = 1; i <= 15; i++) { System.out.println(Thread.currentThread().getName() + "\t" + i); }
            //3.通知：a线程
            number = 1;
            //唤醒a
            a.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}
