package Java并发编程_JavaUtilConcurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @author likeLove
 * @since 2020-09-25  13:13
 */
public class CallAbleDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //1.使用Thread 启动callAble线程
        FutureTask<Integer> task = new FutureTask<>(new MyThread2());
        Thread t1 = new Thread(task, "callable thread");
        //callable thread,启动
        t1.start();
        //获取返回值
        System.out.println("task.get() = " + task.get());
        //如果task没有运行完成就会一直卡在这里（自旋锁）
        while (!task.isDone()) {
        }
        System.out.println("=======callAble运行完成=========");
    }
}

class MyThread1 implements Runnable {
    @Override
    public void run() {

    }
}

class MyThread2 implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        System.out.println("====come in Callable====");
        try {TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) {e.printStackTrace();}
        return 1024;
    }
}

