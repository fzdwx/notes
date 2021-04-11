# NIO基础

nio 非阻塞io  non blocking io

## 1.三大组件



> channel  Buffer

channel类似于stream是读写数据的**双向通道**，可以从channel将数据读入buffer，也可以将buffer中的数据写入channel

常见的channel

- FileChannel
- DatagramChannel
- SocketChannel
- ServerSocketChannel



buffer则用来读写数据，常见的buffer

- ByteBuffer
  - MappedByteBuffer
  - DirectByteBuffer
  - HeapByteBuffer
- ShortBuffer
- IntBuffer
- ···



> selector

但从字面理解不好理解，需要结合服务器的设计演化来理解他的用途

多线程版设计

![image-20210405165751562](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210405165758.png)

缺点：

- 内存占用高
- 线程上下文切换成本高
- 只适合连接少的场景



线程池版本

![image-20210405170303571](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210405170303.png)

缺点

- 一个线程只能处理一个socket连接
- 只适合短连接



selector版

配合一个线程来管理多个channel，获取这些channel上发生的事件，这些channel工作在非阻塞模式下，不会让线程吊死在一个channel上。适合连接数特别多，但流量低的场景 low traffic

![image-20210405170457153](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210405170457.png)

调用selector的select()会阻塞直到channel发生了读写事件，这些事件发生，select()就会返回这些事件交给thread来处理。



## 2.Buffer的基本使用

![image-20210405172800594](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210405172800.png)

```java
public static void  main(String[] args) {
    // 1.获取通道
    try (FileChannel channel = new FileInputStream("data.txt").getChannel()) {
        // 2.准备数据缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        // 3.从channel中读取到buffer
        channel.read(buffer);
        // 4.切换读/写 模式
        buffer.flip();
        while (buffer.hasRemaining()) {
            System.out.print(((char) (buffer.get())));
        }
        // 5.切换为写模式
        buffer.clear();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
```





### byteBuffer结构

- capacity
- position
- limit

![image-20210405173005150](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210405173005.png)

![image-20210405173058522](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210405173058.png)

![image-20210405173139391](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210405173139.png)

![image-20210405173324570](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210405173324.png)

![image-20210405173306564](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210405173306.png)

![image-20210405173455332](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210405173455.png)





### 常用方法

#### 创建一个buffer

```java
ByteBuffer b1 = ByteBuffer.allocate(5);  // class java.nio.HeapByteBuffer
ByteBuffer b2 = ByteBuffer.allocateDirect(10);  //class java.nio.DirectByteBuffer
```

#### get()

~~~
get() 获取position位置的元素
get(int index) 获取索引位置的元素
get(byte[] dst) 在buffer中获取0到dst.length长度的元素
get(byte[] dst,int offset,int length) 获取从offect到offect+length长度的元素
get(int index,byte[] dst) 从index开始，获取dst长度的元素
~~~

![image-20210405195040800](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210405195040.png)



#### rewind()

~~~
从头开始读
~~~

mark & reset

~~~~
mark 做一个标记，记录position位置，
reset是将position重置到mark的位置

对 rewind() 的增强
~~~~

#### 读写模式切换

flip() & clear()



#### 字符串和byteBuffer转换

str -> byteBuffer

```java
ByteBuffer b = StandardCharsets.UTF_8.encode("hello");
ByteBuffer wrap = ByteBuffer.wrap("hello".getBytes(StandardCharsets.UTF_8));
```

byteBuffer -> str	

```java
buffer.flip();
String str = StandardCharsets.UTF_8.decode(buffer).toString();
```





### byteBuffer 粘包和半包

网络上有多条数据发送给服务端，数据之间使用\n进行分隔,网络上有多条数据发送给服务端，数据之间使用\n进行分隔

~~~java
/*
Hello , netty\n
I'm like\n
How are you?\n
变成了下面的两个 byteBuffer(黏包，半包)
Hello , world\nI 'm like\n Ho
w are you?\n
*/
~~~

现在要编写程序，将数据回复成原始的\n分割的数据





```java
public static void main(String[] args) {
    ByteBuffer source = ByteBuffer.allocate(1024);
    String msg1 = "Hello , netty\nI 'm like\n Ho";
    String msg2 = "w are you?\n";

    source.put(msg1.getBytes());
    split(source);
    source.put(msg2.getBytes());
    split(source);
}

private static void split(ByteBuffer source) {
    // 切换模式
    source.flip();
    for (int i = 0; i < source.limit(); i++) {
        if (source.get(i) == '\n') {
            int length = i + 1 - source.position();
            ByteBuffer target = ByteBuffer.allocate(length);
            // 读取source中[0,length)长度的数据到target
            for (int j = 0; j < length; j++) {
                target.put(source.get());
            }
            debugAll(target);
        }
    }
    // 上次未读取的位置开始
    source.compact();
}
```





## 3.文件编程



### FileChannel

不能直接打开FileChannel,必须通过FileInputStream,FileOutputStream或者RandomAccessFile来获取FilChannel，使用他们的getChannel()方法

- RandomAccessFile 是否能读写要根据构造RandomAccessFile时的读写模式决定



**读取**

从channel读取数据填充buffer，返回值表示读取到了多少字节，-1表示文件的末尾

~~~java
int readBytes = channel.read(buffer);
~~~



**写入**

正确姿势

~~~java
Bytebuffer buffer = ByteBuffer.allocate(...);  // 实例化一个buffer
buffer.put(...);    // 在buffer中存入数据
buffer.flip();     // 切换模式
while(buffer.hasReamaining()){
    channel.write(buffer);  // 将buffer中的数据写入channel
}
~~~

在while中调用channel.write是因为不能保证一次就写完.



**关闭**

用try resource方法 自动关闭close



**位置**

~~~java
long pos =  channel.position();
~~~

设置当前位置

~~~java
long newPos = ...;
channel.position(newPos);
~~~

设置当前位置时，如果设置为文件末尾

- 这时读取会返回-1
- 这时写入，会追加内容，但要注意如果position超过了文件末尾，在写入时在新内容和原末尾之间会有空洞(00)



**大小**

使用size方法获取文件的大小



**强制写入**

不会立刻将数据写入磁盘，而是缓存。调用force(true)方法将文件内容和数据（文件的权限等信息）立刻写入磁盘





### 文件复制案例

```java
@Test
void testT1() {
    try (
        FileChannel read = new FileInputStream("D:\\github\\notes\\代码\\data.txt").getChannel();
        FileChannel write = new FileOutputStream("D:\\github\\notes\\代码\\to.txt").getChannel()
    ) {
        // size ： read文件的大小  left : 还剩数据的大小
        long size = read.size();
        for (long left = size; left > 0;) {
            // 底层是零拷贝，效率高，最大2g数据
            left -= read.transferTo(size - left, left, write);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}
```



### Path 以及 Files

- path 用来表示文件路径
- paths是工具类，用来获取path实例



遍历文件夹

```java
Path path = Paths.get("D:\\Java\\jdk-8");

@Test
void testEndWithJar() throws IOException {
    AtomicInteger jarCount = new AtomicInteger();
    Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            if (file.toString().endsWith(".jar")) {
                System.out.println(file);
                jarCount.incrementAndGet();
            }
            return super.visitFile(file, attrs);
        }
    });

    System.out.println("jar 包数量" + jarCount);
}

@Test
void testDirCountAndFileCount() throws IOException {

    AtomicInteger dirCount = new AtomicInteger();
    AtomicInteger fileCount = new AtomicInteger();
    // 循环访问
    Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            System.out.println("===>" + dir);
            dirCount.incrementAndGet();
            return super.preVisitDirectory(dir, attrs);
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            System.out.println(file);
            fileCount.incrementAndGet();
            return super.visitFile(file, attrs);
        }
    });

    System.out.println("文件夹数量" + dirCount);
    System.out.println("文件数量：" + fileCount);
}
```





## 4.网络编程

~~~java
1.处理过的key要及时删除
	iter.remove(); 
2.异常断开的时候要try 且要关闭连接
	 key.cancel();
3.正常关闭，要根据read的长度是否为-1来关闭连接
	int read = client.read(buf);
    	if (read == -1) {
    key.cancel();  // * 正常断开 也要取消连接
    } else {
    	buf.flip();
    }
~~~





### 处理消息边界问题

服务器是一个固定大小的byteBuffer

![image-20210407202458534](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210407202505.png)

1. 固定消息长度，每个数据包的大小都一样，服务器按预定长度读取，缺点是浪费带宽
2. 按分隔符拆分，缺点是效率低
3. TLV格式，即Type类型，Length长度，Value数据，类型和长度已知的情况下，就可以方便获取消息大小，分配合适的buffer，缺点是buffer需要提前分配，如果内容过大，则影响server吞吐量
   - http 1.1 是TLV格式
   - http 2.0 是LTV格式



**扩容**

![image-20210407211338274](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210407211345.png)

写入数据

![image-20210407211359691](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210407211359.png)

测试结果

![image-20210407211413961](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210407211414.png)





### server代码

```java
package com.like.netty.demo;

import org.slf4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import static com.like.netty.util.ByteBufferUtil.debugAll;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Create By like On 2021-04-07 19:06
 */
public class Server {

    private final static Logger log = getLogger(Server.class);

    public static void main(String[] args) throws IOException {
        // 1.创建selector
        Selector selector = Selector.open();
        // 2.创建buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        // 3.创建server
        ServerSocketChannel server = ServerSocketChannel.open();
        // 给server 绑定端口
        server.bind(new InetSocketAddress(8888));
        // 设置server非阻塞
        server.configureBlocking(false);

        // 4.把server和selector建立联系 serverKey就是事件发生后，通过他可以知道和哪个channel有关
        // accept connect read write
        SelectionKey serverKey = server.register(selector, 0, null);
        log.info(Server.class.getName() + "#register(..):register key:{}", serverKey);

        // 只关注accept事件
        serverKey.interestOps(SelectionKey.OP_ACCEPT);
        while (true) {
            // 5.没有事件发生，线程阻塞，有事件，线程才会继续运行
            selector.select();
            // 6.处理事件
            Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
            while (iter.hasNext()) {
                SelectionKey key = iter.next();
                iter.remove();  // * 处理过的key 要删除
                log.info(Server.class.getName() + "#next(..):key: {}", key);

                // 7.区分事件类型
                if (key.isAcceptable()) {  // 连接
                    SocketChannel client = server.accept();
                    client.configureBlocking(false);


                    // 注册client到selector上 监听read事件
                    ByteBuffer buf = ByteBuffer.allocate(16);
                    // 将一个byteBuffer作为附件关联到key
                    SelectionKey clientKey = client.register(selector, 0, buf);
                    clientKey.interestOps(SelectionKey.OP_READ);

                    log.info(Server.class.getName() + "#accept(..):client:{}", client.getRemoteAddress());
                } else if (key.isReadable()) { //  接收客户端的请求
                    SocketChannel client = null;
                    int read = 0;
                    try {
                        client = (SocketChannel) key.channel();
                        ByteBuffer buf = (ByteBuffer) key.attachment();  // 获取这个key的附件
                        read = client.read(buf);
                        if (read == -1) {
                            key.cancel();  // * 正常断开 也要取消连接
                        } else {
                            split(buf);
                            if (buf.position() == buf.limit()) {   // 模拟写入内容过多，buffer扩容
                                ByteBuffer newBuf = ByteBuffer.allocate(buf.capacity() * 2);
                                buf.flip();
                                newBuf.put(buf);
                                key.attach(newBuf);
                            }
                        }
                    } catch (Exception e) {
                        log.warn(Server.class.getName() + "#read catch(..): 断开连接-{}", client.getRemoteAddress());
                        key.cancel();    // * 异常断开 客户端断开后，在selector中的keys真正删除
                    }
                }
            }
        }
    }

    private static void split(ByteBuffer source) {
        // 切换模式
        source.flip();
        for (int i = 0; i < source.limit(); i++) {
            if (source.get(i) == '\n') {
                int length = i + 1 - source.position();
                ByteBuffer target = ByteBuffer.allocate(length);
                // 读取source中[0,length)长度的数据到target
                for (int j = 0; j < length; j++) {
                    target.put(source.get());
                }
                debugAll(target);
            }
        }
        // 上次未读取的位置开始
        source.compact();
    }
}
```





### 利用多线程优化

```java
public class MultiThreadServer {

    public static void main(String[] args) throws IOException {
        Thread.currentThread().setName("Boss");
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        Selector boss = Selector.open();
        SelectionKey bossKey = ssc.register(boss, 0, null);
        bossKey.interestOps(SelectionKey.OP_ACCEPT);
        ssc.bind(new InetSocketAddress(8888));

        ArrayList<Worker> workers = new ArrayList<>();
        workers.add(new Worker("worker-1"));
        workers.add(new Worker("worker-2"));

        while (true) {
            boss.select();
            Iterator<SelectionKey> iter = boss.selectedKeys().iterator();
            while (iter.hasNext()) {
                SelectionKey key = iter.next();
                iter.remove();
                if (key.isAcceptable()) {
                    SocketChannel sc = ssc.accept();
                    System.out.println(sc.getRemoteAddress());
                    sc.configureBlocking(false);
                    // 关联
                    int index = new Random().nextInt(2);
                    System.out.println(index);
                    workers.get(index).register(sc);
                }
            }
        }
    }

    /**
     * 工人
     * @Description: 专门检测读写事件
     */
    static class Worker implements Runnable {
        private Thread thread;
        private Selector worker;
        private String name;

        private volatile boolean start = false; // 还未初始化
        private ConcurrentLinkedQueue<Runnable> queue = new ConcurrentLinkedQueue<>();

        public Worker(String name) {
            this.name = name;
        }

        /**
         * 初始化线程 和selector
         * @param sc
         */
        public void register(SocketChannel sc) throws IOException {
            if (!start) {
                this.thread = new Thread(this, this.name);
                this.worker = Selector.open();
                this.thread.start();

                start = true;
            }
            // 向队列添加任务，不立即执行
            queue.add(() -> {
                try {
                    sc.register(this.worker, SelectionKey.OP_READ, null);
                } catch (ClosedChannelException e) {
                    e.printStackTrace();
                }
            });
            worker.wakeup(); // 唤醒 select 方法
        }

        @Override
        public void run() {
            while (true) {
                try {
                    log.info(Thread.currentThread().getName());

                    worker.select();
                    Runnable scRegisterTask = queue.poll();
                    if (!Objects.isNull(scRegisterTask)) {
                        scRegisterTask.run();  // 执行  sc.register(this.worker, SelectionKey.OP_READ, null);
                    }
                    Iterator<SelectionKey> iter = worker.selectedKeys().iterator();
                    while (iter.hasNext()) {
                        SelectionKey key = iter.next();
                        iter.remove();
                        if (key.isReadable()) {
                            ByteBuffer buffer = ByteBuffer.allocate(16);
                            SocketChannel sc = (SocketChannel) key.channel();
                            int read = sc.read(buffer);
                            if (read == -1) {
                                sc.close();
                            }
                            buffer.flip();
                            debugAll(buffer);
                        } else if (key.isWritable()) {
                            ByteBuffer buffer = ByteBuffer.wrap("this is worker".getBytes());
                            SocketChannel sc = (SocketChannel) key.channel();
                            sc.write(buffer);
                            buffer.flip();
                        }
                    }
                } catch (IOException e) {

                }
            }
        }
    }
}
```





# Netty

是一个异步的，基于事件驱动的网络应用框架，用于快速开发可维护，高性能的网络服务器和客户端。调用时的异步，同步非阻塞，基于多路复用。 Trustin Lee 

Netty在Java网络编程的地位X相当于JavaEE中的Spring

- Cassandra nosql数据库
- Spark 大数据分布式计算
- Hadoop 大数据看分布式存储
- RocketMQ -ali 开源消息队列
- elasticSearch 搜索引擎
- gRPC 
- Dubbo
- Spring 5 flux
- Zookeeper 分布式协调框架









## 1.Server以及Client demo



### server

~~~java
public class NettyServer {

    public static void main(String[] args) {
        // 1.启动器 负责组装netty 组件，启动服务器
        new ServerBootstrap()
                // 2. boosEventLoop workerEventLoop(selector,thread) group 组
                .group(new NioEventLoopGroup())
                // 3. 选择服务器的 serverSocketChannel 实现
                .channel(NioServerSocketChannel.class)
                // 4. boos 负责处理连接 worker(child) 负责处理读写 决定worker 能执行哪些操作
                .childHandler(
                        // 5. channel代表和客户端进行数据读写的通道 initializer 初始化，负责添加别的handler
                        new ChannelInitializer<NioSocketChannel>() {
                            @Override
                            protected void initChannel(NioSocketChannel ch) throws Exception {
                                // 6.添加具体handler
                                ch.pipeline().addLast(new StringDecoder()); // 将ByteBuf 转换为字符串
                                ch.pipeline().addLast(new ChannelInboundHandlerAdapter() { // 自己的自定义handler
                                    @Override
                                    public void channelRead(
                                            ChannelHandlerContext ctx, Object msg) throws Exception {
                                        System.out.println(msg);
                                    }
                                });
                            }
                        })
                // 7.绑定监听端口 8888
                .bind(6666);
    }
}
~~~



### client

~~~java
public class NettyClient {

    public static void main(String[] args) {
        try {
            new Bootstrap()
                    .group(new NioEventLoopGroup())
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new StringEncoder());
                        }
                    })
                    .connect("localhost", 6666)
                    .sync()
                    .channel()
                    .writeAndFlush("hello netty server");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
~~~





### 调用流程

![image-20210410114539392](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210410114546.png)

### 理解

![image-20210410115451427](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210410115451.png)





## 2.组件



### EventLoop

事件循环对象，是一个单线程执行器（同时维护了一个selector），里面有run方法处理channel上源源不断的io时间。

继承关系：

- juc下的SecheduledExecutorService因此包含了线程池中的所有方法
- netty中的orderedWEventExecutor
  - 提供了boolean isEventLoop(Thread thread)方法判断一个线程是否属于次EventLoop
  - 提供parent方法来查看自己属于哪个EventLoopGroup



**EventLoopGroup**

是一组EventLoop，Channel一般会调用EventLoopGroup的register方法来绑定其中一个EventLoop，后续这个Channel上的io事件都由此EventLoop来处理，保证了Io事件处理的线程安全；

- 继承netty自己的EventExecutorGroup
  - 实现iterable接口提高遍历能力
  - next获取下一个EventLoop



#### 在eventLoopGroup中提交任务

```java
public class EventLoopTest {
    private final static Logger log = LoggerFactory.getLogger(EventLoopTest.class);

    @Test
    void testThreadCount() {
        EventLoopGroup boss = new NioEventLoopGroup(4);
        // 1.遍历eventLoop
        /* for (EventExecutor eventExecutor : boss) {
            System.out.println(eventExecutor);
        }*/

        // 2.提交一个普通任务
        boss.next().submit(()->{
            log.info(EventLoopTest.class.getName() + "#testThreadCount(..): 普通任务");
        });

        // 3.提交一个定时任务
        boss.next().scheduleAtFixedRate(()->{
            log.info(EventLoopTest.class.getName() + "#testThreadCount(..): 定时任务");
            // 延迟3秒运行  每隔1秒执行一次
        }, 3, 1, TimeUnit.SECONDS);
    }
}
```

​	

#### boss And workder 和绑定其他group

pipLine 可以绑定一个其他的 eventLoopGroup

```java
public class BossAndWorker {
    private final static Logger log = LoggerFactory.getLogger(BossAndWorker.class);

    public static void main(String[] args) {

        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();

        DefaultEventLoopGroup defaultGroup = new DefaultEventLoopGroup();
        new ServerBootstrap()
            .group(boss, worker)
            .channel(NioServerSocketChannel.class)
            .childHandler(
            new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new StringDecoder());
                    ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelRead(
                            ChannelHandlerContext ctx, Object msg) throws Exception {
                            log.info("#channelRead(..): {}", msg);
                        }
                    });
                    // 可以在添加一個group
                    ch.pipeline().addLast(defaultGroup, new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                            log.info("#channelRead(..):{}",msg);
                        }
                    });
                }
            })
            .bind(8888);
    }
}
```





#### eventLoop是如何切换的

![image-20210410133020080](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210410133020.png)







### channel

channel的主要左右

- close()
- closeFuture() 用来处理channel的关闭
  - sync() 方法作用是同步等待channel关闭
  - addListener() 是异步等待channel方法关闭
- pipline() 方法添加处理器
- write() 写入数据
- writeAndFlush()写入数据并刷出







### Future Promise 

在异步处理时，经常用到这两个接口。

netty中的Future继承于JDK中的Futrure。Promise又对netty Future进行了扩展

- JDK Future 只能同步等待任务结束，才能得到结果
- netty Future可以同步等待任务结束得到结果，也可以异步，但都要等任务结束
- netty Promise不仅有netty Future的功能，而且脱离了任务独立存在，只作为两个线程间穿阿迪结果的容器







### pipline

执行顺序

~~~
inbound  1-2-3 ···

outbount  6-5-4···
~~~



![image-20210410182809952](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210410182810.png)



### EmbeddedChannel

用来测试channel![image-20210410183155957](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210410183156.png)





### byteBuf

会自动扩容

![image-20210410183650499](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210410183650.png)





- 直接内存创建和销毁的代价高昂，但读写性能高（少一次内存复制），配合池化功能一起用
- 直接内存对gc压力小，因为这部分内存不受JVM垃圾回收的管理，但是也要及时主动释放

```java
ByteBuf hBuf = ByteBufAllocator.DEFAULT.heapBuffer();  // 堆内存
ByteBuf dBuf = ByteBufAllocator.DEFAULT.directBuffer(); // 直接内存
```





#### 池化

~~~bash
-Dio.netty.alloctor.type={unpooled | pooled}
~~~



![image-20210410184130928](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210410184131.png)



#### 组成

读取过的

可读部分

可写部分

可扩容部分

![image-20210410184832521](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210410184832.png)





#### 扩容

- 如果写入后的大小没有超过512字节 则按照16的整数倍扩容

  - 8 -> 16
  - 17-> 32

- 如果超过，则安装2 ^ n扩容

  -  513 - > 1024

    



#### 回收

每个byteBuf都会实现ReferenceCounted

调用release回收

在头和为handler 会调用回收









# Netty进阶



## 1.粘包和半包

```java
public static void main(String[] args) {

    NioEventLoopGroup boss = new NioEventLoopGroup();
    NioEventLoopGroup worker = new NioEventLoopGroup();
    new ServerBootstrap()
        .group(boss, worker)
        .channel(NioServerSocketChannel.class)
        // .option(ChannelOption.SO_RCVBUF, 10)  // 设置缓冲区  10个字节
        .childHandler(
        new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {
                ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                    @Override
                    public void channelRead(
                        ChannelHandlerContext ctx, Object msg) throws Exception {
                        System.out.println(msg);
                    }
                });
            }
        })
        // 7.绑定监听端口 8888
        .bind(8888);
}



public static void main(String[] args) {
    try {
        Channel ch = new Bootstrap()
            .group(new NioEventLoopGroup())
            .channel(NioSocketChannel.class)
            .handler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                        @Override
                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                            for (int i = 0; i < 12; i++) {
                                ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
                                buf.writeBytes(new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15});
                                ctx.writeAndFlush(buf);
                            }
                        }
                    });
                }
            })
            .connect("localhost", 8888)
            .sync()
            .channel();
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
}
```

### 粘包现象

![image-20210411105746833](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210411105746.png)

### 半包现象

![image-20210411105845961](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210411105846.png)

![image-20210411105851280](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210411105851.png)



### 滑动窗口

![image-20210411110112370](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210411110112.png)



### 现象分析

粘包

- 现象：发送abc def 接收 abcdef
- 原因
  - 应用层：接收方 ByteBuf设置太大 (netty 默认 1024)
  - 滑动窗口：假设发送方256字节表示一个完整报文，但由于接收方处理不及时且窗口大小足够大，这256字节就会缓冲在接收方的滑动窗口中，当滑动窗口中缓冲了多个报文就会发生粘包
  - Nagle算法：会造成粘包



半包

- 现象：发送 abcdef 接收 abc  def
- 原因：
  - 应用层：接收方ByteBuf小于发送数据的字节大小
  - 滑动窗口：接收方的窗口只有128个字节，但发送了256个字节，放不下了，所以只能先发送128，等ack后在发送剩余的。
  - MSS限制：当发送的数据超过mss限制后，将数据切分发送，造成半包



### 解决方案

> LengthFieldBasedFrameDecoder

1.长度字节从第几个字节开始

2.长度字节占多少个字节

3.长度字节过后几个字节是内容字节

4.前几个字节不要了

~~~
长度字节从第2个字节开始，占2个字节，长度字节后1个字节过后是内容，前3个字节去掉
~~~



![image-20210411113535928](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210411113536.png)





### 测试

```java
public static void main(String[] args) {
    EmbeddedChannel lch = new EmbeddedChannel(
            new LengthFieldBasedFrameDecoder(1024,
                    0,          // 长度字节从0开始
                    4,         // 长度字节占4个
                    1,        // 因为添加了版本号，版本号占一个字节 所以跳过一个字节
                    5),      // 剥离前5个字节
            new LoggingHandler(LogLevel.DEBUG)
    );

    ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();

    writeInBuf(buf,  "Hello,world");
    writeInBuf(buf,  "Hi!");
    writeInBuf(buf, "this is server");

    lch.writeInbound(buf);
}

private static void writeInBuf(ByteBuf buf, String content) {
    byte[] bytes = content.getBytes();
    int length = bytes.length;

    buf.writeInt(length);    // 前4个字节表示长度
    buf.writeByte(1); // 第5个字节表示版本号
    buf.writeBytes(bytes); // 写入的内容
}
```





#### 剥离前5个字节

![image-20210411114917348](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210411114917.png)

#### 剥离前4个字节

![image-20210411114943397](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210411114943.png)

#### 不剥离

![image-20210411115001186](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210411115001.png)





## 2.协议设计和解析

### 模拟redis的协议

模拟redis协议，使用netty发送set命令

```java
static final String LINE = "\r\n";

public static void main(String[] args) {
    try {
       new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                ByteBuf buf = ctx.alloc().buffer();
                                write(buf, "*3");
                                write(buf, "$3");
                                write(buf, "set");
                                write(buf, "$4");
                                write(buf, "name");
                                write(buf, "$4");    // $ +  发送内容的长度
                                write(buf, "like");
                                // set name like
                                ctx.writeAndFlush(buf);
                            }
                        });
                    }
                    @Override
                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                        ByteBuf buf = (ByteBuf) msg;
                        System.out.println(buf.toString(Charset.defaultCharset()));
                    }
                })
                .connect("localhost", 6379);
    } catch (Exception e) {
        e.printStackTrace();
    }
}

private static void write(ByteBuf buf, String value) {
    buf.writeBytes(value.getBytes());
    buf.writeBytes(LINE.getBytes());
}
```

![image-20210411121442226](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210411121442.png)

![image-20210411121456985](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210411121457.png)







### 利用netty提供的http协议

```java
public class TestHttp {

    private final static Logger log = getLogger(TestHttp.class);

    public static void main(String[] args) {

        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBoot = new ServerBootstrap()
                .group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(
                new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                        ch.pipeline().addLast(new HttpServerCodec()); // http 编解码器
                        ch.pipeline().addLast(new SimpleChannelInboundHandler<HttpRequest>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, HttpRequest msg) throws Exception {
                                // 获取请求的信息
                                log.info("#channelRead0(..): uri: {}", msg.uri());

                                // 返回响应信息                                               http协议版本           响应状态
                                DefaultFullHttpResponse resp = new DefaultFullHttpResponse(msg.protocolVersion(), HttpResponseStatus.OK);
                                byte[] content = "<h1>hello my web</h1>".getBytes();
                                resp.content().writeBytes(content);         // 响应信息
                                resp.headers().add(HttpHeaderNames.CONTENT_LENGTH, content.length);   // 请求头  响应长度

                                // 写入channel
                                ctx.writeAndFlush(resp);
                            }
                        });
                        ch.pipeline().addLast(new SimpleChannelInboundHandler<HttpContent>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, HttpContent msg) throws Exception {
                                log.info("#channelRead0(..): HttpContent: {}", msg);
                            }
                        });
                        /* ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                                        @Override
                                        public void channelRead(
                                                ChannelHandlerContext ctx, Object msg) throws Exception {
                                            log.info("#channelRead(..): msg classs :{}", msg.getClass());
                                            // msg classs :class io.netty.handler.codec.http.DefaultHttpRequest
                                            // msg classs :class io.netty.handler.codec.http.LastHttpContent$1
                                            if (msg instanceof HttpRequest) {

                                            } else if (msg instanceof HttpContent) {

                                            } else {

                                            }
                                        }
                                    });*/

                    }
                });
            ChannelFuture cf = serverBoot.bind(80).sync();
            cf.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
```

![image-20210411123403865](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210411123403.png)

![image-20210411123409618](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210411123409.png)





### 自定义协议



