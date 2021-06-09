package cn.like.code.concurrent.case_1;

import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * desc: 相对线程安全实例
 *
 * @author like
 * @date 2021/6/8 13:58
 */
public class VectorUnsafeExample {

    private static Vector<Integer> vector = new Vector<>();

    public static void main(String[] args) {
        final var loopCount = 100;
        final var executor = Executors.newCachedThreadPool();

        // unsafe(loopCount, executor);
        safe(loopCount, executor);

        executor.shutdown();
    }

    /** 未加锁 */
    private static void unsafe(int loopCount, ExecutorService executor) {
        while (true) {
            for (int i = 0; i < loopCount; i++) {
                vector.add(loopCount);
            }
            executor.execute(()->{
                for (int i = 0; i < vector.size(); i++) {
                    vector.remove(i);
                }
            });

            executor.execute(()->{
                for (int i = 0; i < vector.size(); i++) {
                    vector.get(i);
                }
            });
        }
    }

    /** 加锁 */
    private static void safe(int loopCount, ExecutorService executor) {
        while (true) {
            for (int i = 0; i < loopCount; i++) {
                vector.add(loopCount);
            }
            executor.execute(()->{
                synchronized (vector) {
                    for (int i = 0; i < vector.size(); i++) {
                        vector.remove(i);
                    }
                }
            });

            executor.execute(() -> {
                synchronized (vector) {
                    for (int i = 0; i < vector.size(); i++) {
                        vector.get(i);
                    }
                }
            });
        }
    }
}
