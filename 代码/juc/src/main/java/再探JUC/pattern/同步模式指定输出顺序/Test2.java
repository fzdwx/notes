package 再探JUC.pattern.同步模式指定输出顺序;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-03-08 9:32
 */
public class Test2 {

    public static void main(String[] args) {
        AwaitSignal as = new AwaitSignal(5);
        Condition a = as.newCondition();
        Condition b = as.newCondition();
        Condition c = as.newCondition();
        new Thread(() -> {
            as.print(Thread.currentThread().getName(), a, b);
        }, "a").start();
        new Thread(() -> {
            as.print(Thread.currentThread().getName(), b, c);
        }, "b").start();
        new Thread(() -> {
            as.print(Thread.currentThread().getName(), c, a);
        }, "c").start();

        as.lock();
        try {
            a.signal();
        } finally {
            as.unlock();
        }

    }
}

class AwaitSignal extends ReentrantLock {
    private int loopNumber;

    public AwaitSignal(int loopNumber) {
        this.loopNumber = loopNumber;
    }

    public void print(String val, Condition curr, Condition next) {
        for (int i = 0; i < loopNumber; i++) {
            lock();
            try {
                // curr
                curr.await();
                // doSomething
                System.out.print(val);
                // next
                next.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                unlock();
            }
        }
    }
}