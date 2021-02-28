package Java并发编程_JavaUtilConcurrent;

import java.util.concurrent.*;

/**
 * @author likeLove
 * @since 2020-09-25  14:04
 */
public class ThreadPollDemo {
    public static void main(String[] args) {
        //自定义线程池
        ExecutorService threadPool = new ThreadPoolExecutor(2, 5, 1L, TimeUnit.SECONDS,
                                                            new LinkedBlockingQueue<Runnable>(3),
                                                            Executors.defaultThreadFactory(),
                                                            new ThreadPoolExecutor.DiscardPolicy());
        try {
            for (int i = 1; i <= 12; i++) {
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName()+"\t 办理业务");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }

    //jdk创建线程的常用方法
    private static void jdkCreateThreadPool() {
        //1.创建线程池
        /*ExecutorService threadPool = Executors.newFixedThreadPool(5);*///创建固定五个线程的线程池
        /*ExecutorService threadPool = Executors.newSingleThreadExecutor();*///创建只有一个线程的线程池
        ExecutorService threadPool = Executors.newCachedThreadPool();  //创建不知道有多少线程的线程池
        try {
            //运行
            for (int i = 0; i < 10; i++) {
                try {TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) {e.printStackTrace();}
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "\t 办理业务");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭线程池
            threadPool.shutdown();
        }
    }
}
