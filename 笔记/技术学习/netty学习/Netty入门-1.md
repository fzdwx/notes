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