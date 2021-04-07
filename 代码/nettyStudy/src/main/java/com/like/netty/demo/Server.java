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
