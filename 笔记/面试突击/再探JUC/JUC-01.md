#  								并发编程

## 	1.进程和线程

**进程**

- 程序由指令和数据组成，但是这些指令要运行，数据要读写，就必须将指令加载至cpu，数据加载到内存。在指令运行过程中还需要用到磁盘、网络等设备。==进程就是用来加载指令、管理内存和io的==。
- 当一个程序被运行，从磁盘加载这个程序的代码到内存，这时就开启了一个进程。
- 进程可以视为程序的一个实例。大部分的程序可以同时运行多个实例进程，也有的程序只能启动一个实例进程。



**线程**

- 一个进程之内可以分为一到多个**线程**
- 一个线程就是一个指令流，将指令流中的一条条指令以一定的顺序交给CPU 执行
- 在Java中
  - 线程作为最小调度单位
  - 进程作为资源分配最小单位
- 在windows中进程是不活动的，作为线程的容器





**对比**

1. 进程基本上是相互独立的，而线程在进程内部， 是进程的一个子集。
2. 进程拥有共享的资源，如内存空间，内部的线程是共享的
3. 进程间通信比较复杂
   - 同一台计算机的进程通信成为IPC inter - process communication
   - 不同计算机间的通信需要通过网络，并遵守协议。比如HTTP
4. 线程间通信较为简单，因为他们共享进程的内存，多个线程可以访问同一个共享变量
5. 线程更轻量，切换上下文的成本低。



## 2.并行和并发

在单核cpu中，线程实际上是*串行执行*的。操作系统中有一个组件叫任务调度器，将cpu的时间片分给不同的线程使用，cpu在线程的切换很快，所以感觉是同时运行的。==微观串行，宏观并行==。

这种线程轮流使用 cpu 的做法成为 ==并发==，concurrent



- 并发：(concurrent) 是同一时间应对多件事情的能力
- 并行：(parallel) 是同一时间动手做多件事情的能力



## 3.查看进程的方法

**windows** ： 

- tasklist：查看进程
- taskkill：杀死进程

**linux**

- ps -fe ：查看所有进程
- ps -fT -p < pid > 查看某个进程的所有线程
- kill
- top 按大写H切换是否显示线程
- top -H -p < pid > 查看某个进程pid的所有线程 



**Java**

- jps：查看所有Java进程
- jstack 查看某个Java进程的所有线程状态
- jconsole ：连接到Java程序



## 4.上下文切换

**sleep**

1.会让线程从running 进入 time waiting状态

2.其他线程可以调用interrupt方法进行打断，并抛出interruptedExcepiton

3.睡眠结束后会被立即执行



**yield**

1.调用yield会让线程从running进入runnable状态，然后调度执行其他同优先级的线程。如果这时没有同优先级的线程，那么不能保证让当前线程暂停

2.具体的实现依赖操作系统的任务调度器





**join**

~~~java
thread.join()
~~~

表示等待该线程执行完，才能执行后面的内容



## 5.优雅的停止线程

~~~java
package 再探JUC.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-28 11:31
 */
public class Test3 {

    public static void main(String[] args) {
        TwoPhaseTermination tpt = new TwoPhaseTermination();
        tpt.start();

        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (Exception e) {
            e.printStackTrace();
        }

        tpt.stop();
    }
}

@Slf4j
class TwoPhaseTermination {

    /**
     * 监控线程
     */
    private Thread monitor;

    /**
     * 启动监控线程
     */
    public void start() {
        monitor = new Thread(() -> {
            while (true) {
                Thread curr = Thread.currentThread();
                if (curr.isInterrupted()) {
                    log.info("料理后事");
                    break;
                }
                try {
                    TimeUnit.SECONDS.sleep(1);
                    log.info("执行监控记录");
                } catch (Exception e) {
                    e.printStackTrace();
                    // 重新设置打断标记
                    curr.interrupt();
                }
            }
        }, "monitor");

        monitor.start();
    }

    /**
     * 停止监控线程
     */
    public void stop() {
        monitor.interrupt();
    }
}
~~~



![image-20210228113121044](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210228113128.png)

## 6.守护线程

当主线程结束后，守护线程不管运行完了没有都直接结束

![image-20210228115800855](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210228115800.png)

```java
@Slf4j
public class Test5 {

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            log.info("t1 开始运行");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("t1 运行结束");
        }, "t1");
        // 设置为守护线程
        t1.setDaemon(true);
        t1.start();

        log.info("main 开始运行");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("main 运行结束");
    }
}
```



# Montior

## 1.对象头的概念

![image-20210305104656734](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210305104717.png)

![image-20210305104708213](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210305104713.png)



## 2.Monitor-重量级锁

被翻译为监视器或管程。

每个Java对象都可以关联一个Monitor对象，如果使用synchronized给对象上(重量级)之后，该对象的makr word中就被设置指向monitor对象。

![image-20210305105952017](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210305105952.png)

![image-20210305110010613](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210305110010.png)

![image-20210305110045536](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210305110045.png)



~~~
monitor Enter  获取锁
···
monitor Exit   释放锁
~~~





## 3.轻量级锁-案例

![image-20210305113429470](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210305113429.png)

![image-20210305113327344](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210305113327.png)



## 4.轻量级锁

一个对象虽然是多线程访问的，但是访问时间是错开的(也就是没有竞争)，那么可以用轻量级锁来优化。

轻量级锁对使用者是透明的，语法仍然是synchronized

~~~java
static final Object obj = new Object();
public static void m1(){
    synchroized(obj){
        // ···
        m2()
    }
}

public static void m2(){
    synchroized(obj){
        // ···
    }
}
~~~





![image-20210305113934660](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210305113934.png)

![image-20210305114051803](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210305114051.png)

![image-20210305114138654](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210305114138.png)



![image-20210305114318484](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210305114318.png)

![image-20210305114459781](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210305114459.png)





## 5.锁膨胀

如果在尝试加轻量级锁的过程中，cas操作无法成功，这时一种情况就是有其他线程为此对象加上了轻量级锁(有竞争)，这时需要进行锁膨胀，将轻量级锁变为重量级锁

![image-20210305114858428](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210305114858.png)

![image-20210305114935534](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210305114935.png)

![image-20210305115038954](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210305115039.png)



## 6.自旋锁

重量级锁竞争时，可以用自旋锁来优化，如果当前线程自旋成功(即这时候持有锁的线程已经释放了锁)，这时候就可以避免阻塞。

![image-20210305120952416](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210305120952.png)

## 7.偏向锁

轻量级锁在没有竞争时，每次重入仍然要进行cas操作，Java1.6就引入 偏向锁来做进一步优化：只有第一次使用CAS将线程Id设置到对象的mark word头，之后发现这个线程id是自己就表示没有竞争，不用重新cas。以后只要不发生竞争，这个对象就归该线程所有

~~~java
static final Object obj = new Object();
public static void m1(){
    synchroized(obj){
        // ···
        m2()
    }
}

public static void m2(){
    synchroized(obj){
        // ···
        m3()
    }
}
public static void m3(){
    synchroized(obj){
        // ···
        
    }
}
~~~



### 偏向状态

![image-20210305121857789](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210305121857.png)

一个对象创建时：

- 如果开启了偏向锁（默认开启）,那么对象创建后，mark word值为0x05即最后3位为101，这时他的thread、epoch、age都为0
- 偏向锁默认是延迟的，不会在程序启动时立即生效，如果想避免延迟，可以加VM参数 
  - -XX:BiasedLockingStartupDelay=0
- 如果没有开启偏向锁，那么对象创建后，markword值为0x01即最后3位为001,这时他的hashcode、age都为0，第一次用hashcode的时候才会赋值



禁用偏向锁

~~~
-XX:-UseBiasedLocking 
~~~





### 批量重偏向

![image-20210305143411627](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210305143411.png)





### 批量撤销

![image-20210305144152552](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210305144152.png)





## 8.wait、notify

![image-20210306090535038](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210306090542.png)

~~~
主要API： 只有获取锁才能调用这几个方法
	wait():让获取锁的线程到waitSet等待
	notify():让该锁在waitSet里面随机选取一个唤醒
	notify():唤醒所有的线程
~~~







## 9.park、unpark

暂停 ，对应的线程状态-wait

~~~
1.主线程先执行unpark
2.t1线程后执行park
3.t1线程不会停下来
~~~

API：

```java
LockSupport.park();
LockSupport.unpark(Thread.currentThread());
```

![image-20210307092932728](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210307092939.png)

![image-20210307093013324](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210307093013.png)

![image-20210307093250095](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210307093250.png)

![image-20210307093347072](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210307093347.png)



![image-20210307093427068](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210307093427.png)



![image-20210307093436092](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210307093436.png)





# 状态转换

![image-20210307093626873](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210307093627.png)



~~~
1.new -> runnable
	new Thread().start
2.runnable <->  waiting
	obj.wait()   runnable ->  waiting
	obj.notify(),obj.notiflAll(),t.interrupt()
		a.竞争锁失败: waiting -> blocked
		b.竞争锁成功: waiting -> runnable
3.runnable <-> watting 
	当前线程调用t.join()（当前线程在t线程对象的监视器上等额带)当前线程: runnable -> waiting
	t线程运行结束，或当前线程调用了interrupt()，当前线程 waiting -> runable
4.runnable <-> watting 
	当前线程调用LockSupport.part()   当前线程: runnable -> waiting
	当前线程调用LockSupport.unpart() 当前线程 waiting -> runable
~~~

![image-20210307095404118](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210307095404.png)





## 1.死锁

两个线程分别持有A,B锁，但是又想去获得对方的锁，这就是死锁

~~~
怎么定位:
	jps-获取线程号
	jstack pid

~~~





# 模式

## 1.同步模式之保护性暂停

guarded suspension，用在一个线程等待另一个线程的执行结果。

- 有一个结果需要从一个线程传递到另一个线程，让他们关联同一个guarded object
- 如果有结果不断从一个线程到另一个线程那么可以使用消息队列
- jdk中，join、future的实现，采用的就是此模式
- 因为要等待另一方的结果，所以是同步模式

![image-20210306103146906](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210306103147.png)

```java
@Slf4j
public class Test1 {
    public static void main(String[] args) {
        GuardedObject guarded = new GuardedObject();
        new Thread(() -> {
            // 等待结果
            log.info("等待结果");
            Object o = guarded.get(1);
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
@Slf4j
class GuardedObject {
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

@Data
@AllArgsConstructor
class Person {
    private String name;
    private int age;
}
```





### 扩展：在多个类中使用该模式

作为参数传递不方便，所以设计一个中间类，解耦结果等待者和结果生产者，能支持多个任务的管理

![image-20210306110604641](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210306110604.png)

```java
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
```





## 2.异步模式之生产者、消费者

![image-20210306112829251](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210306112829.png)

```java
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
```



## 3.同步模式指定输出顺序

### wait notify

```java
@Slf4j
public class Test {
    public static void main(String[] args) {
        WaitNotify w = new WaitNotify(1, 5);
        new Thread(() -> {
            w.print("a",1, 2);
        }, "1").start(); 
        new Thread(() -> {
            w.print("b", 2, 3);
        }, "2").start();
        new Thread(() -> {
            w.print("c", 3, 1);
        }, "3").start();
    }
}

class WaitNotify {
    private int flag;
    private int loopNumber;

    public WaitNotify(int flag, int loopNumber) {
        this.flag = flag;
        this.loopNumber = loopNumber;
    }

    public void print(String value, int waitFlag, int nextFlag)  {
        for (int i = 0; i < loopNumber; i++) {
            synchronized (this) {
                while (flag != waitFlag) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print(value);
                flag = nextFlag;
                this.notifyAll();
            }
        }
    }
}
```



### Await Signal

```java
public class Test2 {

    public static void main(String[] args) {
        AwaitSignal as = new AwaitSignal(5);
        Condition a = as.newCondition();
        Condition b = as.newCondition();
        Condition c = as.newCondition();
        new Thread(() -> {
            as.print(Thread.currentThread().getName(), a, b);
        }, "a").start();
        new Thread(() -> {
            as.print(Thread.currentThread().getName(), b, c);
        }, "b").start();
        new Thread(() -> {
            as.print(Thread.currentThread().getName(), c, a);
        }, "c").start();

        as.lock();
        try {
            a.signal();
        } finally {
            as.unlock();
        }

    }
}

class AwaitSignal extends ReentrantLock {
    private int loopNumber;

    public AwaitSignal(int loopNumber) {
        this.loopNumber = loopNumber;
    }

    public void print(String val, Condition curr, Condition next) {
        for (int i = 0; i < loopNumber; i++) {
            lock();
            try {
                // curr
                curr.await();
                // doSomething
                System.out.print(val);
                // next
                next.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                unlock();
            }
        }
    }
}
```



### park unpark

```java
public class Test3 {

    static Thread a;
    static Thread b;
    static Thread c;

    public static void main(String[] args) {
        ParkUnPark pu = new ParkUnPark(5);
        a = new Thread(() -> {
            pu.print(Thread.currentThread().getName(), b);
        }, "a");
        b = new Thread(() -> {
            pu.print(Thread.currentThread().getName(), c);
        }, "b");
        c = new Thread(() -> {
            pu.print(Thread.currentThread().getName(), a);
        }, "c");

        a.start();
        b.start();
        c.start();

        LockSupport.unpark(a);
    }
}

class ParkUnPark {

    private int loop;

    public ParkUnPark(int loop) {
        this.loop = loop;
    }

    public void print(String val, Thread next) {
        for (int i = 0; i < loop; i++) {
            LockSupport.park();
            System.out.print(val);
            LockSupport.unpark(next);
        }
    }
}
```



## 4.两阶段终止



```java
public class Test {
    public static void main(String[] args) {
        TwoPhaseTermination tpt = new TwoPhaseTermination();
        tpt.start();

        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (Exception e) {
            e.printStackTrace();
        }

        tpt.stop();
    }
}
```




```java
@Slf4j
class TwoPhaseTermination {
    /**
 * 监控线程
 */
    private Thread monitor;
    private volatile boolean stop = false;

    /**
 * 启动监控线程
 */
    public void start() {
        monitor = new Thread(() -> {
            while (true) {
                Thread curr = Thread.currentThread();
                if (stop) {
                    log.info("料理后事");
                    break;
                }
                try {
                    TimeUnit.SECONDS.sleep(1);
                    log.info("执行监控记录");
                } catch (Exception e) {
                    e.printStackTrace();
                    // 重新设置打断标记
                    curr.interrupt();
                }
            }
        }, "monitor");

        monitor.start();
    }

    /**
 * 停止监控线程
 */
    public void stop() {
        stop = true;
    }
}
```







## 5.异步模式之工作线程（worker-thread）

![image-20210313105002411](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210313105002.png)

![image-20210313110157693](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210313110157.png)


# ReentranLock

可重入锁

~~~
1.可中断
2.可设置超时时间
3.可设置公平锁
4.支持多个条件变量
synchronized也支持可重入
~~~

~~~java
reentranLock.lock()
try{
	//
} finally{
	reentranLock.unlock();
}
~~~



**可重入**

指同一个线程如果首次获取了这把锁，因为他是这个把锁的持有者，所以有权利再次获取这把锁。如果不是可重入锁，那么第二次获取锁的时候，自己也会被锁住





## 1.设置公平锁

```java
ReentrantLock lock = new ReentrantLock(true);
```

公平锁：

按照waitSet里面的顺序获取锁





## 2.条件变量

```java
public class Test7 {
    static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        // 1.创建休息室
        Condition c1 = lock.newCondition();

        lock.lock();
        // 释放锁，进入休息室等待唤醒
        c1.await();
        // 唤醒线程，重新获取锁
        c1.signal();
        c1.signalAll();
    }
}
```

## 3.指定运行顺序

```java
@Slf4j
public class Test7 {
    static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        Condition c1 = lock.newCondition();
        new Thread(() -> {
            lock.lock();
            if (lock.isLocked()) {
                try {
                    c1.await();
                    log.info("1");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            lock.unlock();
        }, "1").start();


        new Thread(() -> {
           while (lock.tryLock()){
               if (lock.isLocked()) {
                   log.info("2");
                   c1.signal();
                   lock.unlock();
                   break;
               }
           }

        }, "2").start();
    }
}
```

### park

```java
@Slf4j
public class Test7 {
    static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            LockSupport.park();
            log.info("1");
        }, "1");

        Thread t2 = new Thread(() -> {
            log.info("2");
            LockSupport.unpark(t1);
        }, "2");

        t1.start();
        t2.start();
    }
}
```







# Java内存模型

JMM

~~~
1.原子性：保存线程不会受到线程上下文切换的影响
2.可见性：保证指令不会收cpu缓存的影响
3.有序性：保存指令不会受到cpu指令并行优化的影响
~~~



可见性：

~~~
volatitle
system.out.println()
sysnchronized
~~~



有序性:

~~~
JVM会在不影响正确性的前提下，可以调整语句的执行顺序，这种特性被称为指令重排
~~~









## volatile

~~~
1.保证可见性：
	可以修饰成员变量和静态成员变量。可以避免线程从自己的工作缓存中查找变量的值，必须主内存中获取他最新的值。
	
~~~

### 原理

- 对写指令 后 会加入写屏障
- 对读指令 前 会加入读屏障



![image-20210308112132653](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210308112933.png)

### double-checked-locking问题

给instance加volatile关键字，防止指令重排

![image-20210308113302315](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210308113302.png)





## 单例模式练习

![image-20210308115907779](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210308115907.png)

~~~
1.防止子类重写，破坏单例
2.添加一个方法
	public Object readResovle(){
		return instance;
	}
3.别的类都可以创建对象，不能
4.能保证
5. 
~~~





# 无锁



## 使用无锁-保护共享资源

~~~
使用原子类可以保证原子性
	AtomicInteger 
~~~

原理：

~~~
1.该类底层运用了CAS（compareAndSet）比较和设置。
2.CAS的底层有是lock cmpxchg指令，在单核和多核cpu下都能保证 比较-交换的原子性
~~~

volatile

~~~
使用Atomic原子类中，变量都用了volatile修饰，保证变量的可见性。
避免线程从自己的工作缓存中查找变量的值，必须要主内存中获取他的值。
~~~

CAS

~~~
1.基于乐观锁的思想：不断重试
2.synchronized是基于悲观锁的控制：锁住变量，只能有一个线程操作
3.CAS体现的是无锁并发、无阻塞并发
~~~





## ABA问题

~~~
1.A初始值为10
2.一个线程修改A为15，然后在修改为10
3.同时另一个线程修改A，希望原本的值为10，然后修改为11.
4.修改成功

但是第二个线程感知不到A有没有被修改过
~~~

**解决**

~~~
增加版本号这个概念，修改一次，版本号+1
使用AtomicStampedReference类
~~~





## 原子累加器

LongAdder

 ~~~java

    /**
     * Table of cells. When non-null, size is a power of 2.
     * 累加单元数组，懒惰初始化
     */
    transient volatile Cell[] cells;

    /**
     * Base value, used mainly when there is no contention, but also as
     * a fallback during table initialization races. Updated via CAS.
     * 基础值，如果没有竞争，就用cas累加这个值
     */
    transient volatile long base;

    /**
     * Spinlock (locked via CAS) used when resizing and/or creating Cells.
     * 在cells 创建或扩容时，设置为1，表示加锁
     */
    transient volatile int cellsBusy;
 ~~~



![image-20210309104612680](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210309104619.png)



![image-20210309104705790](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210309104705.png)

![image-20210309104801202](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210309104801.png)

![image-20210309104853596](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210309104853.png)











# cas实现锁



```java
@Slf4j
public class Test9 {

    public static void main(String[] args) {
        CASLock lock = new CASLock();
        new Thread(() -> {
            log.info("start");
            lock.lock();
            try {
                log.warn("加锁成功");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }finally {
                log.warn("解锁");
                lock.unlock();
            }
            log.info("end");
        }, "t1").start();

        new Thread(() -> {
            log.info("start");
            lock.lock();
            try {
                log.warn("加锁成功");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }finally {
                log.warn("解锁");
                lock.unlock();
            }
            log.info("end");
        }, "t2").start();
    }
}

@Slf4j
class CASLock {
    private AtomicInteger flag = new AtomicInteger(0);

    public void lock() {
        while (true) {
            if (flag.compareAndSet(0, 1)) {
                break;
            }
        }
    }

    public void unlock() {
        flag.set(0);
    }
}
```

101-偏向锁

00-无锁

01-轻量级锁

10-重量级锁





# unsafe

## 1.获取unsafe类，并给一个对象赋值

```java
public class Test10 {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafe.setAccessible(true);
        Unsafe unsafe = (Unsafe) theUnsafe.get(null);

        long idOffset = unsafe.objectFieldOffset(Teacher.class.getDeclaredField("id"));
        long nameOffset = unsafe.objectFieldOffset(Teacher.class.getDeclaredField("name"));

        Teacher t = new Teacher();
        unsafe.compareAndSwapInt(t, idOffset,0,1);
        unsafe.compareAndSwapObject(t, nameOffset, null, "like");

        System.out.println(t);
    }
}

@Data
class Teacher{
    volatile int id;
    volatile String name;
}
```





## 2.实现一个简单的原子int类

```java
class MyAtomicInt {
    private static Unsafe UNSAFE;
    public static long valueOffset;
    private volatile int value;

    public MyAtomicInt(int value) {
        this.value = value;
    }

    static {
        UNSAFE = UnsafeCreator.unsafe();
        try {
            valueOffset = UNSAFE.objectFieldOffset(MyAtomicInt.class.getDeclaredField("value"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public int get() {
        return value;
    }

    public void decrement(int amount) {
        while (true) {
            int prev = this.value;
            int next = prev - amount;

            if (UNSAFE.compareAndSwapInt(this, valueOffset, prev, next)) {
                break;
            }
        }
    }
}

final class UnsafeCreator {
    private static Unsafe unsafe;

    static {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            Unsafe unsafe = (Unsafe) theUnsafe.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static Unsafe unsafe() {
        return unsafe;
    }
}
```





# 享元模式

![image-20210310093356081](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210310093403.png)

```java
class DBPool {
    private int poolSize = 10;

    private Connection[] connections;

    /** 0 空闲  1 繁忙 */
    private AtomicIntegerArray states;

    public DBPool(int poolSize) {
        this.poolSize = poolSize;
        this.connections = new Connection[poolSize];
        this.states = new AtomicIntegerArray(new int[poolSize]);

        for (int i = 0; i < poolSize; i++) {
            connections[i] = new MockConnection();
        }
    }

    /**
     * 借
     * @return {@link Connection}
     */
    public Connection borrow() {
        while (true) {
            for (int i = 0; i < poolSize; i++) {
                if (states.get(i) == 0) {
                    if (states.compareAndSet(i, 0, 1)) {
                        return connections[i];
                    }
                }
            }
            // 没有空闲连接
            synchronized (this) {
                try {
                    this.wait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 归还
     * @param conn
     */
    public void free(Connection conn) {
        for (int i = 0; i < poolSize; i++) {
            if (connections[i] == conn) {
                states.set(i, 0);
                synchronized (this) {
                    this.notifyAll();
                }
                break;
            }

        }

    }
}

class MockConnection implements Connection {}
```





# 并发工具类



## 1.自定义线程池

![image-20210310101329163](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210310101329.png)



### 线程池类

```java
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
```



### 阻塞队列类

```java
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
```



### 拒绝策略

```java
@FunctionalInterface
public interface RejectPolicy<T> {

    void reject(BlockingQueue<T> tasks, T task);
}
```



### 测试方法

```java
@Slf4j
public class Test {

    public static void main(String[] args) {
        ThreadPool threadPool = new ThreadPool(new BlockingQueue<>(5), 3, 10, TimeUnit.MICROSECONDS, (q, t) -> {
            // 1.等待执行 - 死等
//            q.put(t);
            // 2.等待超时
            q.offer(t, 5,TimeUnit.MICROSECONDS);
            // 3.放弃
//            System.out.println("放弃执行"+t);
            // 4.让调用者抛出异常
//            throw new RuntimeException("线程池已满，执行失败"+t);
            // 5.调用者自己执行
//            ((Runnable) t).run();
        });
        for (int i = 0; i < 30; i++) {
            int val = i;
            threadPool.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                log.error("任务执行:{}",val);
            });

        }
    }
}
```



## 2.JDK线程池

![image-20210313093910724](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210313093917.png)

### a.线程池状态

使用int的高三位来表示线程池状态，低29位表示线程数量

目的：减少一次cas操作

![image-20210313094024992](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210313094025.png)



![image-20210313094610502](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210313094610.png)



### b.构造函数

~~~java
public ThreadPoolExecutor(int corePoolSize,
                          int maximumPoolSize,
                          long keepAliveTime,
                          TimeUnit unit,
                          BlockingQueue<Runnable> workQueue,
                          ThreadFactory threadFactory,
                          RejectedExecutionHandler handler) {
    if (corePoolSize < 0 ||
        maximumPoolSize <= 0 ||
        maximumPoolSize < corePoolSize ||
        keepAliveTime < 0)
        throw new IllegalArgumentException();
    if (workQueue == null || threadFactory == null || handler == null)
        throw new NullPointerException();
    this.acc = System.getSecurityManager() == null ?
        null :
    AccessController.getContext();
    this.corePoolSize = corePoolSize;
    this.maximumPoolSize = maximumPoolSize;
    this.workQueue = workQueue;
    this.keepAliveTime = unit.toNanos(keepAliveTime);
    this.threadFactory = threadFactory;
    this.handler = handler;
}
~~~

![image-20210313095415237](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210313095415.png)



### c.创建多少线程更合适

1. 过小容易导致饥饿
2. 过大会导致线程上下文切换，占用内存更多



- CPU密集型运算

![image-20210313111101217](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210313111101.png)

- io密集型

![image-20210313111134477](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210313111134.png)



### d.任务调度线程池

![image-20210313114317414](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210313114317.png)





# Fork/Join

1.**概念**：

是1.7新加入的线程池实现。体现的是一种分治思想，适用于能够进行任务拆分的cpu密集型运算。

所谓任务拆分，就是将一个大任务拆分为算法上相同的小任务，直到不能拆分可以直接求解。

![image-20210313114907708](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210313114907.png)





```java
public class Test12 {
    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool(3);

        System.out.println(pool.invoke(new MyTask(4)));
    }
}

class MyTask extends RecursiveTask<Integer> {

    private int n;

    public MyTask(int n) {
        this.n = n;
    }

    @Override
    protected Integer compute() {
        if (n <= 0) {
            return 1;
        }
        MyTask myTask = new MyTask(n - 1);
        myTask.fork();
        return n + myTask.join();
    }
}
```

![image-20210314103324637](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210314103331.png)







# AQS

abstract Queue Synchronizer 是阻塞式锁和相关的同步器工具的框架

- 使用==state==属性来表示资源的状态（独占模式和共享模式），子类需要定义如何维护这个状态，控制获取锁和释放锁
  - compareAndSetState cas机制设置state状态
  - 独占模式只让一个线程，共享模式可以让多个线程访问
- 提供了基于FIFO的等待队列，类似于Monitor的EntryList
- 条件变量来实现等待、唤醒机制，支持多个条件变量，类似于Monitor的waitSet

![image-20210314104130849](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210314104130.png)



## 获取释放锁

![image-20210314104233271](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210314104233.png)



todo 原理部分没看









# StampedLock

![image-20210314115954592](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210314115954.png)



# Semaphore

最多允许n个线程同时访问

![image-20210314120858870](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210314120859.png)



# CountdownLatch

当计数为0，等待的线程开始运行

![image-20210314121117430](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210314121117.png)





# CyclicBarrier

当线程到达指定的数量才运行