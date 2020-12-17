package Java并发编程_JavaUtilConcurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author likeLove
 * @since 2020-09-25  12:20
 * 使用阻塞队列来演示生产者和消费者
 */
public class ProduceAndConsumerWithBlockQueueDemo {

    public static void main(String[] args) {
        MyResource mr = new MyResource(new ArrayBlockingQueue<String>(10));
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t 生产线开始启动");
            try {
                mr.produce();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "produce").start();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t 消费线开始启动");
            try {
                mr.consumer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "consumer").start();
        try {TimeUnit.MICROSECONDS.sleep(500); } catch (InterruptedException e) {e.printStackTrace();}
        try {
            mr.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("main 5s时间到 叫停结束");
    }
}

class MyResource {
    private volatile boolean flag = true;//默认开启，进行生产和消费
    private AtomicInteger atomicInt = new AtomicInteger();
    private BlockingQueue<String> blockingQueue = null;

    public MyResource(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
        System.out.println("blockingQueue ClassName = " + blockingQueue.getClass().getName());
    }

    public void setBlockingQueue(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    //生产
    public void produce() throws Exception {
        String data = null;
        boolean offer;
        while (flag) {
            //产生数据
            data = atomicInt.incrementAndGet() + "";
            //添加到阻塞队列中
            offer = blockingQueue.offer(data, 2L, TimeUnit.SECONDS);
            //判断是否添加成功
            if (offer) {
                System.out.println(Thread.currentThread().getName() + "\t 插入队列" + data + "成功");
            }
            else {
                System.out.println(Thread.currentThread().getName() + "\t 插入队列" + data + "失败");
            }
        }
        System.out.println(Thread.currentThread().getName() + "\t flag = false ,生产结束");
    }

    //消费
    public void consumer() throws Exception {
        String poll = null;
        while (flag) {
            poll = blockingQueue.poll(2L, TimeUnit.SECONDS);
            if (poll == null || poll.equalsIgnoreCase("")) {
                flag = false;
                System.out.println(Thread.currentThread().getName() + "\t 超过2秒中没有取到商品，退出");
                System.out.println();
                System.out.println();
                return;
            }
            System.out.println(Thread.currentThread().getName() + "\t 消费商品" + poll + "成功");
        }
    }
    //停止所有活动
    public void stop() throws Exception {
        this.flag = false;
    }
}
