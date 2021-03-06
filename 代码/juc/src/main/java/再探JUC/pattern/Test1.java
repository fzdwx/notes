package 再探JUC.pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author like
 * @email 980650920@qq.com
 * @Description: 同步模式之保护性暂停
 * @since 2021-03-06 10:35
 */
@Slf4j
public class Test1 {
    public static void main(String[] args) {
        GuardedObject guarded = new GuardedObject();
        new Thread(() -> {
            // 等待结果
            log.info("等待结果");
            Object o = guarded.get(2);
            log.info("结果是:{}", o);
        }, "t1").start();
        new Thread(() -> {
            try {
                log.info("执行下载");
                TimeUnit.SECONDS.sleep(3);
                // 下载完成
                log.info("下载完成");
                guarded.complete(new Person("like", 18));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "t2").start();
    }
}

/**
 * 保护的对象
 * @author pdd20
 * @date 2021/03/06
 */
class GuardedObject {
    private Object response;

    public Object get(long timeout) {
        synchronized (this) {
            long start = System.currentTimeMillis();
            long passedTime = 0;
            while (response == null) { // 没有结果
                long waitTime = timeout - passedTime;  // 应该等待的时间
                if (waitTime <= 0) break;  // 超时了
                try {
                    this.wait(waitTime);  // 等待结果
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                passedTime = System.currentTimeMillis() - start;
            }
            return response;
        }
    }

    public void complete(Object response) {
        synchronized (this) {
            this.response = response; // 处理完成
            this.notifyAll(); // 唤醒
        }
    }
}

@Data
@AllArgsConstructor
class Person {
    private String name;
    private int age;
}