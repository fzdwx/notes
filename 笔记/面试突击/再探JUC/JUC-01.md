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



