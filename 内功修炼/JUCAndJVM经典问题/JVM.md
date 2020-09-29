# 一、常见垃圾回收算法

1.  引用计数

    ![image-20200926152922257](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200926152922.png)

2.  复制

    ![image-20200926154415402](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200926154415.png)

3.  标记清除

    ![image-20200926163841825](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200926163841.png)

4.  标记整理

    ![image-20200926163928925](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200926163929.png)



# 二、什么是GCRoots

![image-20200926172741194](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200926172741.png)

**什么是垃圾**

内存中已经不在被使用到的空间就是垃圾

**可达性分析**

1.  复制：
2.  标记-清除
3.  标记-压缩

![image-20200926181426121](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200926181426.png)



**可以作为gcroot的对象**

![image-20200926181729420](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200926181729.png)



# 三、如何查看JVM系统默认值

**标准参数**：

java -version

java -help



x**参数**

java -Xint：解释执行

java -Xcomp：第一次使用就编译成本地代码

java -Xmixed：混合模式



XX**参数**

-   boolean型：+表示启动，-表示关闭

```java
-Xms128m == -XX:InitialHeapSize=268435456 
-Xmx2024m==  -XX:MaxHeapSize=4263510016
-XX:ReservedCodeCacheSize=240m
-XX:+UseConcMarkSweepGC
-XX:SoftRefLRUPolicyMSPerMB=50
-ea
-XX:CICompilerCount=2
-Dsun.io.useCanonPrefixCache=false
-Djdk.http.auth.tunneling.disabledSchemes=""
-XX:+HeapDumpOnOutOfMemoryError
-XX:-OmitStackTraceInFastThrow
-Djdk.attach.allowAttachSelf=true
-Dkotlinx.coroutines.debug=off
-Djdk.module.illegalAccess.silent=true
```

## 如何查看一个java程序，他的jvm参数是多少

~~~java
jps -l
jinfo -flag PrintGCDetails 25812
-XX:-PrintGCDetails
~~~



## 查看所有参数

~~~java
java -XX:+PrintFlagsInitial
java -XX:+PrintFlagsFinal
java -XX:+PrintCommandLineFlags
~~~



## 常用参数

-   -Xms：初始堆内存大小                 ==-XX:IntialHeapSize==
-   -Xmx：最大内存                             ==-XX:MaxHeapSize==
-   -Xss：设置单个线程栈大小            ==-XX:ThreadStackSize==
-   -Xmn：新生代大小



![image-20200926210158685](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200926210158.png)



# 四、引用类型

![image-20200926210812709](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200926210812.png)

## 1.强引用

默认

对于强引用的对象：==就算是出了OOM也不会进行回收，死都不回收==，是最常见的对象引用，只要有一个强引用指向对象，就不能回收

```java
Object o1 = new Object();
```



## 2.软引用

内存够用就保留，不够就回收

```java
Object o2 = new Object();
SoftReference<Object> softRef = new SoftReference<Object>(o2);
```

 

## 3.弱引用

gc的时候直接回收

```java
Object o3 = new Object();
WeakReference<Object> weakRef = new WeakReference<>(o3);
o3 = null;
System.out.println(weakRef.get());
System.gc();
System.out.println("========");
System.out.println(weakRef.get());
```



## 4.虚引用

 和referenceQueue一起使用，主要作用是跟踪对象被垃圾回收的状态，提供一种确保对象被 finalize以后，做某些事的机制。PhantomReference的get方法总是返回null，因此无法访问对应的引用对象，其意义在与说明一个对象已经进入finalization阶段。可以被gc回收，用来实现比finalization机制更灵活的回收操作。

作用：在这个对象被回收的时候收到一个系统通知或者后续添加更进一步的处理，有点像Spring Aop 的 after







## 5.总结

![image-20200927110531412](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200927110538.png)



# 五、谈谈你对OOM的认识

![image-20200927110916447](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200927110916.png)





# 六、垃圾回收器和回收算法

**gc算法**

引用计数，复制，标记清除，标记整理

**垃圾回收器**

**串行 serial**：单个垃圾收集线程进行，用户线程是暂停的

**并行 parallel**：多个垃圾收集器进行

**并发标记清除  cms**：用户线程和垃圾回收线程是同时进行的，可能交替进行，

**g1**



怎么查看默认垃圾回收器

>java -XX:+PrintCommandLineFlags -version
>
>java 8 ：并行
>
>java 14：g1



![image-20200927145712738](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200927145712.png)



## 1.新生代

### serial 串行gc 

serial，serial Copying

serial：

-XX:+UserSerialGC

一个单线程的垃圾收集器，必须暂停其他线程，所有的工作线程都要等待它收集结束

是client模式下默认的新生代的垃圾收集器

![image-20200927161622846](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200927161623.png)



### 并行收集器ParNew

串行收集器的并行版本

![image-20200927162130772](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200927162131.png)