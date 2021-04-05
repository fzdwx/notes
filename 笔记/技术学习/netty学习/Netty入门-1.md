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
String str = StandardCharsets.UTF_8.decode(buffer).toString();
```





## byteBuffer 粘包和半包

网络上有多条数据发送给服务端，数据之间使用\n进行分隔,网络上有多条数据发送给服务端，数据之间使用\n进行分隔

~~~java
/*
Hello , world\n
I'm zhangsan\n
How are you?\n
变成了下面的两个 byteBuffer(黏包，半包)
Hello , world\nI 'm zhangsan\n Ho
w are you?\n
*/
~~~

现在要编写程序，将数据回复成原始的\n分割的数据

123