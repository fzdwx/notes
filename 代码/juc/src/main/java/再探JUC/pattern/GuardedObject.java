package 再探JUC.pattern;

import lombok.extern.slf4j.Slf4j;

/**
 * 保护的对象
 * @author pdd20
 * @date 2021/03/06
 */
@Slf4j
public class GuardedObject {
    private Object response;

    public Object get(long timeout) {
        synchronized (this) {
            long start = System.currentTimeMillis();
            long passedTime = 0;
            while (response == null) { // 没有结果
                long waitTime = timeout - passedTime;  // 应该等待的时间
                if (waitTime <= 0) {
                    log.error("获取返回结果超时了"); // 超时了
                    break;
                }
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