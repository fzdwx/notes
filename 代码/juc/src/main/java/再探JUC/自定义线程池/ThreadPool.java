package 再探JUC.自定义线程池;

import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.concurrent.TimeUnit;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-03-10 11:00
 */
@Slf4j
public class ThreadPool {

    /** 存放任务的容器 */
    private BlockingQueue<Runnable> tasks;

    /** 核心线程数 */
    private int coreSize;

    /** 线程接到任务的超时时间 */
    private long timeout;

    /** 时间单位 */
    private TimeUnit timeUnit;

    private RejectPolicy<Runnable> rejectPolicy;

    private final HashSet<Worker> workers = new HashSet<>();

    public ThreadPool(
            BlockingQueue<Runnable> tasks, int coreSize, long timeout, TimeUnit timeUnit, RejectPolicy policy) {
        this.tasks = tasks;
        this.coreSize = coreSize;
        this.timeout = timeout;
        this.timeUnit = timeUnit;
        this.rejectPolicy = policy;
    }

    public void execute(Runnable task) {
        // 当任务数没有超过 coreSize，直接交给worker执行
        // 如果超过就加入任务容器
        synchronized (workers) {
            if (workers.size() < coreSize) {
                Worker worker = new Worker(task);
                log.info("新增worker{},{}", worker, task);
                workers.add(worker);
                worker.start();
            } else {
                // 拒绝策略
                tasks.tryPut(rejectPolicy, task);
            }
        }
    }

    class Worker extends Thread {
        private Runnable task;

        public Worker(Runnable task) {
            this.task = task;
        }

        @Override
        public void run() {
            // 执行任务
            // 1.执行task
            // 2.执行tasks中的任务
            while (task != null || (task = tasks.poll(timeout, timeUnit)) != null) {
                try {
                    log.info("正在执行...{}", task);
                    task.run();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    task = null;
                }
            }
            synchronized (workers) {
                log.info("worker 被移除{}", this);
                workers.remove(this);
            }
        }
    }
}
