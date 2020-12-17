package Java并发编程_JavaUtilConcurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author likeLove
 * @since 2020-09-24  19:38
 */
public class BlockingQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> synchronousQueue = new SynchronousQueue<>();
        new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName()+"\t put 1");
                synchronousQueue.put("1");
                System.out.println(Thread.currentThread().getName() + "\t put 2");
                synchronousQueue.put("2");
                System.out.println(Thread.currentThread().getName() + "\t put 3");
                synchronousQueue.put("3");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "A").start();
        new Thread(() -> {
            try {
               /* try {TimeUnit.SECONDS.sleep(5); } catch (InterruptedException e) {e.printStackTrace();}*/
                System.out.println(Thread.currentThread().getName() + "\t take 1");
                synchronousQueue.take();
                System.out.println(Thread.currentThread().getName() + "\t take 2");
                synchronousQueue.take();
                System.out.println(Thread.currentThread().getName() + "\t take 3");
                synchronousQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "B").start();
    }

    private static void 超时() throws InterruptedException {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
        System.out.println(blockingQueue.offer("1", 2L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("2", 2L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("3", 2L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("4", 2L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.poll(2L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.poll(2L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.poll(2L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.poll(2L, TimeUnit.SECONDS));
    }

    private static void 阻塞() throws InterruptedException {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
        blockingQueue.put("1");
        blockingQueue.put("2");
        blockingQueue.put("3");
        /* System.out.println("======= 阻塞 =======");*/
        System.out.println("==== take =====");
        System.out.println(blockingQueue.take());
        blockingQueue.put("4");
        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());
    }

    private static void 不抛出异常组() {
        //List list = new ArrayList();
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
        System.out.println(blockingQueue.offer("a"));
        System.out.println(blockingQueue.offer("b"));
        System.out.println(blockingQueue.offer("c"));
        System.out.println(blockingQueue.offer("d"));
        blockingQueue.forEach(System.out::println);
        System.out.println("===========remove============");
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        blockingQueue.forEach(System.out::println);
        System.out.println(blockingQueue);
    }

    //出现错误会直接抛出异常
    private static void 抛出异常组(BlockingQueue<String> blockingQueue) {
        System.out.println(blockingQueue.add("a"));
        System.out.println(blockingQueue.add("b"));
        System.out.println(blockingQueue.add("c"));
        /*System.out.println(blockingQueue.add("d"));*/
        blockingQueue.forEach(System.out::println);
        System.out.println("===========remove============");
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        blockingQueue.forEach(System.out::println);
        System.out.println(blockingQueue);
    }
}
