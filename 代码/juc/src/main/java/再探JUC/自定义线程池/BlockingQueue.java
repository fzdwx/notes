package 再探JUC.自定义线程池;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-03-10 10:14
 */
@Slf4j
public class BlockingQueue<T> {

    /** 容量 */
    private int capacity;

    public BlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    /** 队列  任务容器 */
    private final Deque<T> queue = new ArrayDeque<>();
    /** 锁 */
    private final ReentrantLock lock = new ReentrantLock();

    /** 生产者条件 */
    private final Condition fullWaitSet = lock.newCondition();

    /** 消费者条件 */
    private final Condition emptyWaitSet = lock.newCondition();

    /**
     * 带超时的获取任务
     * @param timeout 超时
     * @param unit 单位
     * @return {@link T}
     */
    public T poll(long timeout, TimeUnit unit) {
        timeout = unit.toNanos(timeout);

        T task = null;
        lock.lock();
        try {
            while (queue.isEmpty()) {
                try {
                    // 返回的剩余时间
                    if (timeout <= 0) {
                        return task;
                    }
                    timeout = emptyWaitSet.awaitNanos(timeout);// 容器是空的，等待生产者生产
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            task = queue.removeFirst();  // 消费
            fullWaitSet.signal();  // 生产者生成
        } finally {
            lock.unlock();
        }
        return task;
    }

    /**
     * 带超时的存放任务
     * @param task 任务
     * @param timeout 超时
     * @param unit 单位
     * @return boolean
     */
    public boolean offer(T task, long timeout, TimeUnit unit) {
        timeout = unit.toNanos(timeout);

        lock.lock();
        try {
            while (queue.size() == capacity) {
                try {
                    if (timeout <= 0) {
                        log.info("等待超时，任务被丢弃:{}", task);
                        return false;
                    }
                    timeout = fullWaitSet.awaitNanos(timeout);   // 容器满了，等待消费者消费
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            queue.addLast(task);     // 生产
            emptyWaitSet.signal();  // 消费者消费
        } finally {
            lock.unlock();
        }

        return true;
    }

    /**
     * 阻塞式的获取任务
     * @return {@link T}
     */
    public T take() {
        T task = null;
        lock.lock();
        try {
            while (queue.isEmpty()) {
                try {
                    emptyWaitSet.await();  // 容器是空的，等待生产者生产
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            task = queue.removeFirst();  // 消费
            fullWaitSet.signal();  // 生产者生成
        } finally {
            lock.unlock();
        }
        return task;
    }

    /**
     * 阻塞式的存放任务
     * @param task 任务
     */
    public void put(T task) {
        lock.lock();
        try {
            while (queue.size() == capacity) {
                try {
                    fullWaitSet.await();   // 容器满了，等待消费者消费
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            queue.addLast(task);     // 生产
            emptyWaitSet.signal();  // 消费者消费
        } finally {
            lock.unlock();
        }
    }

    /**
     * 容器大小
     * @return int
     */
    public int size() {
        lock.lock();
        try {
            return queue.size();
        } finally {
            lock.unlock();
        }

    }

    /**
     * 尝试存放
     * @param policy 政策
     * @param task 任务
     */
    public void tryPut(RejectPolicy<T> policy, T task) {
        lock.lock();
        try {
            // 1.满了就调用策略处理任务
            if (queue.size() == capacity) {
                log.info("线程池已满，根据策略进行处理：{}", task);
                policy.reject(this, task);
            } else {
                // 2.没满就放入队列等待处理
                queue.addLast(task);     // 生产
                emptyWaitSet.signal();  // 消费者消费r
            }
        } finally {
            lock.unlock();
        }

    }
}
