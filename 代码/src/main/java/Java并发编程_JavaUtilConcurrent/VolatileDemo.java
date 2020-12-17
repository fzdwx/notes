package Java并发编程_JavaUtilConcurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author likeLove
 * @time 2020-09-18  10:21
 * Volatile是jvm提供的轻量级的同步机制
 * <p>
 * -   保证可见性
 * -   不保证原子行
 * -   禁止指令重排(有序)
 */

public class VolatileDemo {
    public static void main(String[] args) {

    }

    //2.不保证原子性
    private static void noAtomicity() {
        MyData data = new MyData();
        for (int i = 1; i <= 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    data.add();
                    data.addAtomic();
                }
            }, String.valueOf(i)).start();
        }
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //如果保证原子性，结果应该是10000
        System.out.println(data.number);
        System.out.println(data.atomicInteger);
    }

    /**
     * 1.Volatile保证可见性的测试
     */
    private static void seeVolatile() {
        MyData data = new MyData();
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t come in");
            try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
            data.addNumber();
            System.out.println(Thread.currentThread().getName() + "\t update number:" + data.number);
        }, "addData").start();
        while (data.number == 0) { }
        System.out.println(Thread.currentThread().getName() + "\t over");
    }
}

class MyData {
    volatile int number = 0;
    //保证原子性的加
    AtomicInteger atomicInteger = new AtomicInteger();

    public void addAtomic() {
        this.atomicInteger.getAndIncrement();
    }

    public void add() {
        this.number += 1;
    }

    public void addNumber() {
        this.number = 60;
    }
}

class Singleton {
    private Singleton() {
        System.out.println(Thread.currentThread().getName() + "\t Singleton的构造方法");
    }

    private static volatile Singleton instance = null;

    public static  Singleton getInstance() {
        //第一次检查
        if (instance == null) {
            synchronized (Singleton.class) {
                //第二次检查
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        for (int i = 1; i < 10; i++) {
            new Thread(() -> {
                Singleton.getInstance();
            }, String.valueOf(i)).start();
        }
    }
}
