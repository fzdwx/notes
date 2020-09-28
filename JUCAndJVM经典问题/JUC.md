![image-20200917203225646](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200917203225.png)

![image-20200917203313163](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200917203313.png)





# 一、volatile是什么？

是jvm提供的轻量级的同步机制

-   保证可见性
-   不保证原子行
-   禁止指令重排(有序)



## 1、JMM-Java内存模型

Java Memory Model(java内存模型)；本身是一种抽象的概念本不存在，是一组规范,顶一个程序中各个变量（包括实例字段，静态字段和构成数组对象的元素）的访问方式

**关于同步的规定：**

1.  线程解锁前，必须把共享的变量的值刷新会主内存
2.  线程加锁前，必须读取主内存中最新的值到自己的工作内存中
3.  锁是同一把锁

由于jvm运行程序的实体是线程，而每个线程创建时jvm都会为其创建一个工作内存，工作内存是 线程的私有数据区域，而Java内存模型中规定所有变量都存储在主内存，主内存是共享区域，所有线程都可以访问，==但线程对变量的操作必须在工作内存中进行，首先将变量从工作内存中读取到自己的工作区域，进行操作且完成后，在将变量刷新回主内存==，而不能直接操作柱内存中的变量，各个线程中的工作内存是==主内存中的变量的副本==，因此不同线程无妨相互访问变量，线程之间的通信（传值）不许通过主内存完成



## 2、JMM三大特性

1.  可见性
2.  原子性
3.  可见性



## 3、Volatile-可见性

数据发生改变马上就会刷新到主内存中，其他线程就能读取到这个数据的最新值，没加volatile程序就会一直运行下去

![image-20200918103857527](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200918103904.png)

```java
public class VolatileDemo {
    public static void main(String[] args) {
        MyData data = new MyData();
        new Thread(() ->{
            System.out.println(Thread.currentThread().getName()+"\t come in");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            data.addNumber();
            System.out.println(Thread.currentThread().getName()+"\t update number:"+data.number);
        },"addData").start();

        while (data.number == 0) {

        }
        System.out.println(Thread.currentThread().getName()+"\t over");
    }
}
class MyData {
    int number = 0;

    public void addNumber() {
        this.number = 60;
    }
```

加上volatile关键字

![image-20200918104005424](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200918104005.png)



## 4、Volatile-原子性

volatile不保证原子性

所有的操作都是有效的，不可分割，完整性，某个线程正在做某个业务时，中间不可以被分割。需要整体完整，要么同时成功，要么同时失败

```java
public static void main(String[] args) {
    MyData data = new MyData();
    for (int i = 1; i < 10; i++) {
        new Thread(() -> {
            for (int j = 0; j < 1000; j++) {
                data.add();
            }
        }, String.valueOf(i)).start();
    }
    try {
        TimeUnit.SECONDS.sleep(5);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    //如果保证原子性，结果应该是9000
    System.out.println(data.number);
}
```

![image-20200918111007719](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200918111007.png)

结果为8571，即volatile不保证原子性



### 4.2、解决原子性

1.  加sync
2.  atomic

![image-20200918113824539](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200918113824.png)

![image-20200918113928185](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200918113928.png)





## 5、Volatile-禁止指令重排序（有序）

volatile实现==禁止指令重排优化==，从而避免多线程环境下程序出现乱序执行的现象，先了解一个概念：==内存屏障==(Memory Barrier)是一个CPU指令，作用：

-   保证特定操作的执行顺序
-   保证某些变量的内存可见性(利用这个特性实现volatile的内存可见性)

在指令间插入一条==Memory Barrier==则会告诉编译器和CPU，不管什么指令都不能和这条==Memory Barrier==指令排序，==通过插入内存屏障禁止在内存屏障前后的指令执行重排优化==，另一个作用是强制刷新各种CPU的缓存数据

![image-20200918123753133](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200918123753.png)

计算机在执行程序时，为了提高性能，编译器和处理器常常会对==指令重排==

![image-20200918115201836](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200918115201.png)

单线程环境里面确保程序最终执行结果和代码顺序执行的结果一致

处理器在进行重排序时必须要考虑指令之间的==数据依赖性==

多线程环境中线程交替执行，由于编译器优化重拍的存在，两个线程中使用的变量能否保证一致性是无法确定的，结果无法预测

![image-20200918120156219](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200918120156.png)



## 6、单例模式-volatile

```java
class Singleton {
    private Singleton() {
        System.out.println(Thread.currentThread().getName()+"\t Singleton的构造方法");
    }
    private static  Singleton instance = null;
    public static Singleton getInstance() {

        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }

    public static void main(String[] args) {
        for(int  i = 1;i<10;i++){
            new Thread(()->{
                Singleton.getInstance();
            },String.valueOf(i)).start();
        }
    }
}
```

### 不加锁

![image-20200918125241636](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200918125241.png)

### 加锁

![image-20200918125412789](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200918125412.png)



### 双重检查

```java
public static  Singleton getInstance() {
    //第一次检查
    if (instance == null) {
        synchronized (Singleton.class) {
            //第二次检查
            if (instance == null) {
                instance = new Singleton();
            }
        }
    }
    return instance;
}
```

![image-20200918125610048](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200918125610.png)





### 防止指令重排

双重检查不一定完全安全，因为有指令重排的存在，加入volatile

![image-20200918131826263](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200918131826.png)





# 二、CAS(compare and swap)

## compareAndSet

**概念：**比较和交换

```java
private static void cas() {
    AtomicInteger number = new AtomicInteger(5);
    //compareAndSet(int 期望值, int 修改后的值)，如果实际值和期望值相同，就修改
    System.out.println(number.compareAndSet(4, 2020));
    System.out.println(number);
}
false
5
```



## increment

**unsafe：**

是cas的核心类，由于java无法直接访问底层系统，需要通过本地方法来访问，unsafe相当于一个后门，基于该类可以直接操作特定内存的数据。==unsafe存在于sun.misc包中==,其内部方法操作==可以像c的指针一样直接操作内存==。==unsafe类的所有方法都是native修饰的==，也就是说==unsafe类中的方法都直接调用操作系统底层资源执行相应任务==。

**valueofset**：

表示该变量在内存中的==偏移地址==，因为unsafe就是根据内存偏移地址获取数据的

```java
private static void increment() {
    // public final int getAndIncrement() {
    //        return unsafe.getAndAddInt(this, valueOffset, 1);
    //    }
    System.out.println(number.getAndIncrement());
    System.out.println(number);
}
5
6
```



## compareAndSwap

是一条cpu并发原语，功能是判断内存某个位置的值是否为预期值，如果是就更改为新的值，原子。

cas并发原语体现在java中的unsafe类中的各个方法。调用unsafe类中的cas方法，jvm会帮我们==实现cas汇编指令==。这是一种完全依赖硬件的功能，通过它实现了原则操作。

由于cas是一种系统语言，原语属于操作系统用于范畴，是由若干条指令组成的，是用于完成某个功能的一个过程，并且==原语的执行过程必须是连续的，在执行过程中不可被打断，也就是cas是一条cpu的原子操作，不会造成数据不一致的问题==。

atomicInteger的自增加1的方法会调用unsafe类的getAndAddInt方法。

这个方法实现的一个简单自旋锁

1.  先找出需要修改的值
2.  进行CAS操作
3.  直到预期的值和需要修改的值相同就进行修改

**案例：**

初始值为5，两个线程都需要对这个值进行+1

A线程睡了1秒钟，B线程直接进行+1操作，变为6

A线程醒了，发现需要修改的值是5，但是现在变成了6，就不能修改成功，所以只能重新读取在来一遍

因为atomicInteger中的value 是用volatile修饰的具有可见性，所以线程A可以读取到最新值6

**为什么不用sync而用cas**

sync每次只能只有一个线程进行操作



```java
private static void increment() {
    public final int getAndIncrement() {
        return unsafe.getAndAddInt(this, valueOffset, 1);
    }
    // var1   this
    // var2   valueofset(在内存中的地址)
    // var4   需要变动的量
    // var5   通过var1 和 var2 找出的内存中真实的值
    public final int getAndAddInt(Object var1, long var2, int var4) {
        int var5;
        do {
            var5 = this.getIntVolatile(var1, var2);
        } while(!this.compareAndSwapInt(var1, var2, var5, var5 + var4));

        return var5;
    }
    System.out.println(number.getAndIncrement());
    System.out.println(number);
}
```



## 缺点

1.  如果失败，会一直进行循环，如果长时间失败，会给cpu带来很大的消耗
2.  只能保证一个共享变量的原子操作
3.  ABA问题



## ABA问题

**问题的产生**

比如说两个线程同时对一个值进行操作，a线程把值变为2，然后又把值变为原来的数，当线程b进行操作的时候发现值并没有变化，就继续对这个值进行CAS操作。

但是这个操作是不正确的，这就是ABA问题

```java
AtomicStampedReference<User> userAtomicStampedReference = new AtomicStampedReference<>();
```





# 三、集合类不安全的问题

## 1.ArrayList

底层是Array

### 问题出现：

多个线程访问同一个集合类

### 抛出：

ConcurrentModificationException 

```java
private static void unSafe() {
    List<String> list = new ArrayList<>();
    for (int i = 0; i < 3; i++) {
        new Thread(() -> {
            list.add(UUID.randomUUID().toString().substring(0,8));
            System.out.println(list);
        }, "name").start();
    }
}
```

![image-20200922184057145](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200922184057.png)

![image-20200922184108945](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200922184109.png)

![image-20200922184215912](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200922184215.png)

### 解决方案

1.  使用vector类

2.  使用集合工具类中的方法：Collections.synchronizedList(list);

3.  使用copyOnwriteArrayList类：new CopyOnWriteArrayList<>()

    1.  写时复制，读写分离的思想

        往一个容器添加元素的时候，不直接往容器中添加，而是先将当前容器进行copy，复制出一个新的容器，然后往新的容器中添加元素，在将原容器指向新容器。这样的好处是对copyOnWrite容器进行并发的读，而不需要加锁，因为容器不会发生改变。

    2.  使用volatile关键字在存储数据的数组

    3.  在方法上使用lock锁





## 2.HashSet

底层是HashMap

```java
public boolean add(E e) {
    return map.put(e, PRESENT)==null;
}
```







## 3.HashMap

concurrentHashMap







# 四、锁

**公平锁**：

多个线程按照申请锁的顺序来获取锁，类似排队打饭，先来后到

**非公平锁**：

多个线程获取锁的顺序并不是安装申请锁的顺序，有可能后申请的线程比先申请的线程优先获取锁。在高并发的清空下，有可能造成==优先级反转或者饥饿现象==

**可重入锁，递归锁**

同一线程外层函数获取锁后，内层递归函数仍然能获取锁。在同一个线程在外层方法获取锁的时候，在进入内层方法会自动获取锁。

线程可以进入任何一个他已经拥有的锁所同步着的代码块

## 自旋锁：

```java
public final Object getAndSetObject(Object var1, long var2, Object var4) {
    Object var5;
    do {
        var5 = this.getObjectVolatile(var1, var2);
    } while(!this.compareAndSwapObject(var1, var2, var5, var4));

    return var5;
}
```

```java
private final static SpinLockDemo sld = new SpinLockDemo();

AtomicReference<Thread> reference = new AtomicReference<>();

public void myLock() {
    Thread thread = Thread.currentThread();
    System.out.println("come in " + thread.getName());
    while (!reference.compareAndSet(null, thread)) {

    }
}
public void myUnLock() {
    Thread thread = Thread.currentThread();
    while (reference.compareAndSet(thread, null)) {

    }
    System.out.println("un lock " + thread.getName());
}
public static void main(String[] args) {
    new Thread(() -> {
        sld.myLock();
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sld.myUnLock();
    },"A").start();
    new Thread(() -> {
        sld.myLock();
        sld.myUnLock();
    },"B").start();
}
```



**独占锁**：

这锁一次只能一次被一个线程所持有，对reentrantlock和sync而言都是独占锁

**共享锁**：

这个锁可以被多个线程所持有

reentrantReadWriteLock的读锁是共享锁，写锁是独占锁

读锁的共享锁可保证并发读的效率是非常高效的。读写，写读，写写的过程是互斥的，读读能共存





## 读写锁案例

```java
package JavaUtilConcurrent;
/**
 * @author likeLove
 * @since 2020-09-24  14:50
 * 多个线程同时读一个资源类没有任何问题，所以为了满足并发量，读取共享资源应该可以进行。
 * 如果有一个线程想去写共享资源，就不应该在有其他线程可以对该资源进行读或写
 * 读写不能共存
 * 写写不能共存
 * 读读能共存
 * 写操作：原子+独占，整个过程必须是一个完整的统一体，中间不能被分割，打断
 */
public class ReadWriterLockDemo {
    public static void main(String[] args) {
        MyCache myCache = new MyCache();
        //写入数据
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            new Thread(() -> {
                myCache.put(finalI + "", finalI + "");
            }, String.valueOf(i)).start();
        }
        //读取数据
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            new Thread(() -> {
                myCache.get(finalI + "");
            }, String.valueOf(i)).start();
        }

    }
}
//资源类
class MyCache {
    //存放数据
    private volatile static Map<String, Object> map = new HashMap<>();
    //锁
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    //写入
    public void put(String key, Object value) {
        //写入锁
        lock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t 正在写入" + key);
            try {TimeUnit.MICROSECONDS.sleep(300); } catch (InterruptedException e) {e.printStackTrace();}
            map.put(key, value);
            System.out.println(Thread.currentThread().getName() + "\t 写入完成");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.writeLock().unlock();
        }

    }
    //读取
    public void get(String key) {
        //读取锁
        lock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t 正在读取" + key);
            try {TimeUnit.MICROSECONDS.sleep(300); } catch (InterruptedException e) {e.printStackTrace();}
            map.get(key);
            System.out.println(Thread.currentThread().getName() + "\t 读取完成");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.readLock().unlock();
        }
    }
    //清除缓存
    public void clear() {
        map.clear();
    }
}
```

不加写入锁：

别人可以插队

![image-20200924155520271](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200924155527.png)

加写入锁

![image-20200924155606398](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200924155606.png)





## CountDownLatch

传入一个数，表示有多少个线程

**案例**：

传入6，表示运行完6个线程之后，main线程才能运行

CountDownLatch count = new CountDownLatch(6);

```java
public class CountDownLatchDemo {
    public static void main(String[] args) {
        CountDownLatch count = new CountDownLatch(6);
        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t 国,被灭");
                count.countDown();
            },Country.get(i) ).start();
        }
        try {
            count.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "\t  秦国统一");
    }
}
enum Country {
    //国家枚举
    ONE(1, "齐"), TWO(2, "楚"), three(3, "燕"),four(4,"赵"),five(5,"魏"),six(6,"韩");
    public static String get(int index){
        Country[] arr = Country.values();
        return arr[index].s;
    }
    private int i;
    private String s;
    Country(int i, String s) {
        this.i = i;
        this.s = s;
    }
    public int getI() {
        return i;
    }
    public String getS() {
        return s;
    }
}
```



## CyclicBarrier 

CyclicBarrier cyclicBarrier = new CyclicBarrier(6);

当线程数到达6时，主线程才运行

```java
public class CyclicBarrierDemo {
    static CyclicBarrier cb = new CyclicBarrier(7, () -> {
        System.out.println("集齐7颗龙珠，召唤神龙");
    });
    public static void main(String[] args) {
        for (int i = 0; i < 7; i++) {
            int finalI = i;
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t 收集到第" + finalI + "龙珠");
                try {
                    //阻塞
                    cb.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            },String.valueOf(i) ).start();
        }
    }
}
```



## Semaphore

**概念：**

限制一次能用多少个线程

1.  用于多个共享资源的互斥使用。
2.  用于并发线程数的控制

**案例：**

一次只能有3个线程同时运行

```java
public static void main(String[] args) {
    //几个停车位，是否是公平锁（默认不是）
    Semaphore semaphore = new Semaphore(3);
    for (int i = 1; i <= 6; i++) {
        new Thread(() -> {
            try {
                semaphore.acquire();
                System.out.println(Thread.currentThread().getName()+"\t 抢到车位");
                try {TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) {e.printStackTrace();}
                System.out.println(Thread.currentThread().getName() + "\t 停车3秒后离开");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                semaphore.release();
            }
        }, String.valueOf(i)).start();
    }
}
2	 抢到车位
3	 抢到车位
1	 抢到车位
3	 停车3秒后离开
1	 停车3秒后离开
2	 停车3秒后离开
4	 抢到车位
6	 抢到车位
5	 抢到车位
5	 停车3秒后离开
4	 停车3秒后离开
6	 停车3秒后离开
```



# 五、阻塞队列

什么时候需要唤醒线程，blockingQueue一手包办

blockingQueue是接口

![image-20200924194349527](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200924194349.png)

![image-20200924194814128](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200924194814.png)

![image-20200924195237408](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200924195237.png)

## 常用实现类

### 1.ArrayBlockingQueue

由数组数据结构组成的有界阻塞队列



### 2.LinkedBlockingQueue

由链表数据结构组成的有界阻塞队列，界限为（integer.MAX_VALUE）

![image-20200924194708400](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200924194708.png)



### 3.PriorityBlockingQueue

支持优先级排序的无界阻塞队列



### 4.LinkedBlockingDeque

双端队列

### 5.SynchronousQueue

生产一个消费一个

```java
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
```

![image-20200924203755511](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200924203755.png)

## 常用API

### 1、调用抛出异常组方法-add remove 

出现错误会直接抛出异常

```java
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
```



### 2、不抛出异常组-offer poll

```java
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
```



### 3、阻塞-put take

![image-20200924201540265](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200924201540.png)

```java
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
```



### 4、超时

超过2秒中就直接返回

```java
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
```



## synchronized 和 lock的区别

1.  synchronized是java的关键字，lock是java类

2.  sync不需要手动释放，lock需要手动释放锁
3.  sync不可终端，lock可以中断
4.  sync和lock都是默认非公平锁，但是lock可以在构造函数的时候传入一个boolean
5.  lock有condition

**需求**：

1.  A运行五次，b运行10次，c运行15次
2.   循环10次，顺序不变
3.  精确唤醒

```java
public class SyncAndLockDemo {
    public static void main(String[] args) {
        ShareData data = new ShareData();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                data.print5();
            }
        }, "A").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                data.print10();
            }
        }, "B").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                data.print15();
            }
        }, "C").start();
    }
}

class ShareData {
    private int number = 1; //A=1,B=2,C=3
    private Lock lock = new ReentrantLock();
    private Condition a = lock.newCondition();//解锁A
    private Condition b = lock.newCondition();//解锁b
    private Condition c = lock.newCondition();//解锁c
    public void print5() {
        lock.lock();
        try {
            //1.判断：如果不是1就等待
            while (number != 1) { a.await(); }
            //2.干活：输出5次
            for (int i = 1; i <= 5; i++) { System.out.println(Thread.currentThread().getName() + "\t" + i); }
            //3.通知：b线程
            number = 2;
            //唤醒b
            b.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    public void print10() {
        lock.lock();
        try {
            //1.判断：如果不是2就等待
            while (number != 2) { b.await(); }
            //2.干活：输出10次
            for (int i = 1; i <= 10; i++) { System.out.println(Thread.currentThread().getName() + "\t" + i); }
            //3.通知：c线程
            number = 3;
            //唤醒c
            c.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    public void print15() {
        lock.lock();
        try {
            //1.判断：如果不是3就等待
            while (number != 3) { c.await(); }
            //2.干活：输出5次
            for (int i = 1; i <= 15; i++) { System.out.println(Thread.currentThread().getName() + "\t" + i); }
            //3.通知：a线程
            number = 1;
            //唤醒a
            a.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
```







# 六、线程池

1.  继承Thread
2.  实现Ruunable
3.  实现Callable
4.  使用线程池创建



## 1.callAble：

```java
public static void main(String[] args) throws ExecutionException, InterruptedException {
    //1.使用Thread 启动callAble线程
    FutureTask<Integer> task = new FutureTask<>(new MyThread2());
    Thread t1 = new Thread(task, "callable thread");
    //callable thread,启动
    t1.start();
    //获取返回值
    System.out.println("task.get() = " + task.get());
    //如果task没有运行完成就会一直卡在这里（自旋锁）
    while (!task.isDone()) {
    }
    System.out.println("=======callAble运行完成=========");
}
class MyThread2 implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        System.out.println("====come in Callable====");
        try {TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) {e.printStackTrace();}
        return 1024;
    }
}
```



## 2.线程池

主要是控制运行的线程的数量，处理过程中将任务放进队列，然后在线程创建后启动这些任务，如果线程数量超过了最大数量，超出的线程进行排队等候，等待其他线程运行完毕，在继续执行

**特点：**

1.  线程复用
2.  控制最大并发数量
3.  管理线程

降低资源损耗，通过重复利用已经创建的线程降低线程创建和销毁造成的消耗

提高响应速度，当任务到达时，任务可以不需要等到线程创建就能立即执行

提高线程的可管理性，如果无限的创造线程，不仅会消耗系统资源，还会降低系统的稳定性，使用线程池可以进行统一个分配，调优和监控

![image-20200925144742369](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200925144749.png)

## 3.创建线程池的方法

1.  new ThreadPoolExecutor()

2.  Executors.newFixedThreadPool(int)

    ==固定数的线程==

3.  Executors.new SingleThreadExecutor()

    ==一个线程==

4.  Executros.newCachedThreadPool()

    ==可以扩容的线程池==

线程池工具类创建线程的原理是使用阻塞队列

```java
public static ExecutorService newFixedThreadPool(int nThreads) {
    return new ThreadPoolExecutor(nThreads, nThreads,
                                  0L, TimeUnit.MILLISECONDS,
                                  new LinkedBlockingQueue<Runnable>());
}
public static ExecutorService newSingleThreadExecutor() {
    return new FinalizableDelegatedExecutorService
        (new ThreadPoolExecutor(1, 1,
                                0L, TimeUnit.MILLISECONDS,
                                new LinkedBlockingQueue<Runnable>()));
}
public static ExecutorService newCachedThreadPool() {
    return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                                  60L, TimeUnit.SECONDS,
                                  new SynchronousQueue<Runnable>());
}
```



```java
public static void main(String[] args) {
    //1.创建线程池
    /*ExecutorService threadPool = Executors.newFixedThreadPool(5);*///创建固定五个线程的线程池
    /*ExecutorService threadPool = Executors.newSingleThreadExecutor();*///创建只有一个线程的线程池
    ExecutorService threadPool = Executors.newCachedThreadPool();  //创建不知道有多少线程的线程池
    try {
        //运行
        for (int i = 0; i < 10; i++) {
            try {TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) {e.printStackTrace();}
            threadPool.execute(() -> {
                System.out.println(Thread.currentThread().getName() + "\t 办理业务");
            });
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        //关闭线程池
        threadPool.shutdown();
    }
}
```



## 4.线程池创建原理

**参数**：

1.   corePoolSize：线程池中常驻的线程数
2.  maximumPoolSize：这个线程池最大能容纳的线程
3.  keepAliveTime：线程停止运行时，存活的时间
4.  unit：设定线程存活时间的单位
5.  workQueue：阻塞队列，线程等待所呆的区域
6.  threadFactory：创建线程的工厂，默认的就行
7.  handler：拒绝策略

```java
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
```



**运行流程：**

1.  创建了线程池后，等待提交过来的任务请求
2.  当调用execute()方法添加一个请求任务时，线程会做如下判断
    1.  如果正在运行的线程数量小于corePoolSize，那么马上会创建线程运行这个任务
    2.  如果大于或者等于corePoolSize，那么就将这个任务放进阻塞队列中
    3.  如果阻塞队列慢了且正在运行的线程数量还小于maxmumPoolSize，那么还是要创建非核心线程执行这个任务
    4.  如果队列满且正在运行的线程数量大于或等于maximumPoolSize，那么线程池会启动饱和策略来拒绝执行
3.  当一个线程完成任务时，它会从队列中取出下一个任务来执行
4.  当一个线程无事可做超过一定时间（keepAliveTime），就会关闭这个线程，最终线程数会收缩到核心线程数的大小





## 5.线程池的拒绝策略

**出现条件：**

阻塞队列已经满了，线程池中的max线程数也达到了，这个时候就许要拒绝策略来处理这个问题

**4种策略：**

1.  abortPolicy(默认)：直接抛出RejectedExecutionException异常阻止系统的正常运行
2.  CallerRunPolicy：既不会抛弃任务，也不会抛出异常，而是将某些任务回退到调用者
3.  DiscardOldestPolicy：抛弃队列中等待最久的任务
4.  DiscardPolicy：直接丢弃任务，不做任何处理，如果运行丢弃这是最好的方案





## 6.自定义线程池

```java
public static void main(String[] args) {
    //创建线程池
    ExecutorService threadPool = 
        new ThreadPoolExecutor(2, 5, 1L,TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(3),Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy());
    //启动
    try {
        for (int i = 1; i <= 12; i++) {
            threadPool.execute(() -> {
                System.out.println(Thread.currentThread().getName()+"\t 办理业务");
            });
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        threadPool.shutdown();
    }
}
```



**拒绝策略：**

AbortPolicy：

![image-20200926121803208](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200926121810.png)



CallerRunsPolicy

![image-20200926121827279](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200926121827.png)



DiscardOldestPolicy

![image-20200926121858091](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200926121858.png)



DiscardPolicy

![image-20200926121918570](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200926121918.png)





### CPU密集型：

8+1

![image-20200926143132627](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200926143132.png)

### IO密集型

8 * 10

![image-20200926142851057](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200926142851.png)





# 七、死锁

**概念**：

指两个或两个以上的进程在执行过程中，因争夺资源而造成的一种==相互等待==的现象。若无外力干涉那它们都将无法推进下去，如果系统资源充足，进程的资源请求都能得到满足，死锁出现的可能性就很低，否则就会因为争夺有限的资源陷入死锁。

![image-20200926143714730](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200926143714.png)

## 案例

```java
/**
 * 持有自己的，想要别人的
 */
class MyDeadData implements Runnable {
    private String lockA;
    private String lockB;
    public MyDeadData(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }
    @Override
    public void run() {
        //持有一个锁
        synchronized (lockA) {
            System.out.println(Thread.currentThread().getName() + "\t 持有：" + lockA + "\t 尝试获取" + lockB);
            try {TimeUnit.SECONDS.sleep(2); } catch (InterruptedException e) {e.printStackTrace();}
            //想要获取另一把锁
            synchronized (lockB) {
                System.out.println(Thread.currentThread().getName() + "\t 持有：" + lockB + "\t 尝试获取" + lockA);
            }
        }
    }
}
//测试
public class DeadlockDemo {
    public static void main(String[] args) {
        String a = "lock A";
        String b = "lock B";
        new Thread(new MyDeadData(a, b), "线程A").start();
        new Thread(new MyDeadData(b, a), "线程B").start();
    }
}
```

![image-20200926144850455](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200926144850.png)



## 怎么判断是不是死锁

### jps -l

~~~bash
D:\Java\project\work>jps -l
18644 org.jetbrains.jps.cmdline.Launcher
15240 JavaUtilConcurrent.DeadlockDemo
26552 jdk.jcmd/sun.tools.jps.Jps
25964
~~~

### jstack 15240

~~~bash
D:\Java\project\work>jstack 15240
2020-09-26 14:55:03
Full thread dump Java HotSpot(TM) 64-Bit Server VM (25.151-b12 mixed mode):

"DestroyJavaVM" #14 prio=5 os_prio=0 tid=0x0000000002df3800 nid=0x5fe8 waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

 ········
 
===================================================
"线程B":
        at JavaUtilConcurrent.MyDeadData.run(DeadlockDemo.java:38)
        - waiting to lock <0x000000076b71f598> (a java.lang.String)
        - locked <0x000000076b71f5d0> (a java.lang.String)
        at java.lang.Thread.run(Thread.java:748)
"线程A":
        at JavaUtilConcurrent.MyDeadData.run(DeadlockDemo.java:38)
        - waiting to lock <0x000000076b71f5d0> (a java.lang.String)
        - locked <0x000000076b71f598> (a java.lang.String)
        at java.lang.Thread.run(Thread.java:748)

Found 1 deadlock.

~~~

