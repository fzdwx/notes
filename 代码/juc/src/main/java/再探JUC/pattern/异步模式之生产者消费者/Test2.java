package 再探JUC.pattern.异步模式之生产者消费者;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-03-06 11:32
 */
public class Test2 {
    public static void main(String[] args) {
        MessageQueue q = new MessageQueue(2);
        for (int i = 0; i < 3; i++) {
            int id = i;
            new Thread(() -> {
                q.put(new Message(id, "第" + id + "个消息"));
            }, "生产者:" + i).start();
        }
        new Thread(() -> {
            while (true) {
                q.take();
            }
        }, "消费者:").start();
    }
}

@Slf4j
class MessageQueue {
    private final LinkedList<Message> queue = new LinkedList<>();
    private int capacity;

    public MessageQueue(int capacity) {
        this.capacity = capacity;
    }

    public Message take() {
        synchronized (queue) {
            while (queue.isEmpty()) {
                try {
                    log.info("队列为空，消费者等待");
                    queue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // 取出
            Message msg = queue.removeFirst();
            log.info("消费了一个消息:{}", msg);
            queue.notify(); // 通知其他线程，继续生产
            return msg;
        }
    }

    public void put(Message message) {
        synchronized (queue) {
            while (queue.size() == capacity) {
                try {
                    log.info("队列满了，生产者等待");
                    queue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // 添加
            queue.addLast(message);
            log.info("生产了一个消息:{}", message);
            queue.notify(); // 通知其他线程，继续消费
        }
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
final class Message {
    public int id;
    private Object msg;
}