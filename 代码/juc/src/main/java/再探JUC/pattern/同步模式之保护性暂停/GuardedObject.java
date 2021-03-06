package 再探JUC.pattern.同步模式之保护性暂停;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 保护的对象
 * @author pdd20
 * @date 2021/03/06
 */
@Slf4j
@NoArgsConstructor
@Data
public class GuardedObject {

    private int id;
    private Object response;

    public GuardedObject(int id) {
        this.id = id;
    }

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

class MailBoxes {
    private static Map<Integer, GuardedObject> boxes = new Hashtable<>();

    private static int id = 1;

    private static synchronized int generateId() {
        return id++;
    }

    public static GuardedObject createGuardedObject() {
        GuardedObject o = new GuardedObject(generateId());
        boxes.put(o.getId(), o);
        return o;
    }

    public static Set<Integer> getIds() {
        return boxes.keySet();
    }

    public static GuardedObject getGuardedObject(int id) {
        GuardedObject go = boxes.get(id);
        boxes.remove(id);
        return go;
    }
}

@Slf4j
class People extends Thread {
    @Override
    public void run() {
        // 收信
        GuardedObject guarded = MailBoxes.createGuardedObject();
        log.info("开始收信 id:{}", guarded.getId());
        Object mail = guarded.get(3000);
        log.info("收到信:{}", mail);
    }
}

@Slf4j
@AllArgsConstructor
class Postman extends Thread {
    private int id;
    private String mail;

    @Override
    public void run() {
        GuardedObject guarded = MailBoxes.getGuardedObject(id);
        log.info("开始送信 id:{},内容:{}", guarded.getId(),mail);
        guarded.complete(mail);
    }
}

class  test{
    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            new People().start();
        }
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (Integer id : MailBoxes.getIds()) {
            new Postman(id, "第" + id + "的信封").start();
        }
    }
}