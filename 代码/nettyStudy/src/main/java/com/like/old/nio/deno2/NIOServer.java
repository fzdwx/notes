package com.like.old.nio.deno2;

import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author like
 * @date 2021-02-01 12:54
 * @contactMe 980650920@qq.com
 * @description
 */
@Slf4j
public class NIOServer {
    public static void main(String[] args) {
        try (ServerSocketChannel server = ServerSocketChannel.open()) {

            Selector selector = Selector.open();

            // 1.设置监听的端口号
            server.bind(new InetSocketAddress(6666));

            // 2.设置为非阻塞
            server.configureBlocking(false);

            // 3.server 注册到 selector => 关心时间为op_accept 连接事件
            server.register(selector, SelectionKey.OP_ACCEPT);

            // 4.等待客户连接
            while (true) {
                if (selector.select(1000) == 0) { // 没有事件发生      if (selector.selectNow() == 0) { // 没有事件发生
                    //                    log.error("服务器等待了1秒钟，无客户端连接···");
                    continue;
                }
                // 5.已经获取到关注的事件 通过keys反向获取通道
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> keyI = keys.iterator();
                while (keyI.hasNext()) {
                    SelectionKey key = keyI.next();
                    if (key.isAcceptable()) {   // a.连接事件 op.accept
                        SocketChannel client = server.accept();
                        // * 设置为非阻塞
                        client.configureBlocking(false);
                        // 6.将客户端注册到selector，关注事件为op.read并关联一个buffer
                        client.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                    }

                    if (key.isReadable()) {  // b. op.read
                        // 7.通过key获取到channel和我们放入的buffer
                        SocketChannel client = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        client.read(buffer);
                        // 8.读取数据
                        client.read(buffer);
                        System.out.println("from client:" + new String(buffer.array()));
                        buffer.clear();
                    }

                    // 9.移除当前key，防止重复操作
                    keyI.remove();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
