package Java并发编程_JavaUtilConcurrent;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author likeLove
 * @since 2020-09-24  14:50
 * 多个线程同时读一个资源类没有任何问题，所以为了满足并发量，读取共享资源应该可以进行。
 * 如果有一个线程想去写共享资源，就不应该在有其他线程可以对该资源进行读或写
 * 读写不能共存
 * 写写不能共存
 * 读读能共存
 * 写操作：原子+独占，整个过程必须是一个完整的统一体，中间不能被分割，打断
 */
public class ReadWriterLockDemo {
    public static void main(String[] args) {
        MyCache myCache = new MyCache();

        //写入数据
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            new Thread(() -> {
                myCache.put(finalI + "", finalI + "");
            }, String.valueOf(i)).start();
        }
        //读取数据
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            new Thread(() -> {
                myCache.get(finalI + "");
            }, String.valueOf(i)).start();
        }
    }
}
//资源类
class MyCache {
    //存放数据
    private volatile static Map<String, Object> map = new HashMap<>();
    //锁
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    //写入
    public void put(String key, Object value) {
        //写入锁
        lock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t 正在写入" + key);
            try {TimeUnit.MICROSECONDS.sleep(300); } catch (InterruptedException e) {e.printStackTrace();}
            map.put(key, value);
            System.out.println(Thread.currentThread().getName() + "\t 写入完成");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.writeLock().unlock();
        }

    }
    //读取
    public void get(String key) {
        //读取锁
        lock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t 正在读取" + key);
            try {TimeUnit.MICROSECONDS.sleep(300); } catch (InterruptedException e) {e.printStackTrace();}
            map.get(key);
            System.out.println(Thread.currentThread().getName() + "\t 读取完成");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.readLock().unlock();
        }
    }
    //清除缓存
    public void clear() {
        map.clear();
    }
}
