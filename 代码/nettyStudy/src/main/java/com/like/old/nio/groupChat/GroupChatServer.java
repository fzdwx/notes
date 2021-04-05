package com.like.old.nio.groupChat;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @author like
 * @date 2021-02-01 17:20
 * @contactMe 980650920@qq.com
 * @description
 */
@Slf4j
public class GroupChatServer {
    /** 监听的端口号 **/
    public static final int PORT = 8888;
    private Selector selector;
    private ServerSocketChannel listen;

    public GroupChatServer() {
        try {
            // 1.得到选择器以及绑定端口
            selector = Selector.open();
            listen = ServerSocketChannel.open();
            listen.bind(new InetSocketAddress(PORT));
            // 2.设置非阻塞
            listen.configureBlocking(false);
            // 3.listen注册到选择器上
            listen.register(selector, SelectionKey.OP_ACCEPT);

            log.info("服务器创建成功···");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new GroupChatServer().doListen();
    }

    public void doListen() {
        try {
            while (true) {
                int count = selector.select(2000);
                if (count > 0) {  // 有事件处理
                    Iterator<SelectionKey> iKeys = selector.selectedKeys().iterator();
                    while (iKeys.hasNext()) {
                        SelectionKey key = iKeys.next();

                        // 1.监听连接事件
                        if (key.isAcceptable()) {
                            SocketChannel client = listen.accept();
                            client.configureBlocking(false);

                            client.register(selector, SelectionKey.OP_READ);
                            log.info(client.getRemoteAddress().toString().substring(1) + " 进入聊天室！");
                        }
                        // 2.可读状态
                        if (key.isReadable()) {
                            // 发送消息
                            readMessage(key);
                        }

                        iKeys.remove();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readMessage(SelectionKey key) {
        SocketChannel client = null;
        try {
            client = (SocketChannel) key.channel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);

            int count = client.read(buffer);
            if (count > 0) {
                String msg = new String(buffer.array());
                System.out.println("from " + client.getRemoteAddress().toString().substring(1) + ":" + msg);
                // 转发消息到其他客户端
                toOtherClients(msg, client);
            }
        } catch (IOException e) {
            try {
                log.info(client.getRemoteAddress() + "离线了!");
                key.cancel();
                client.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    private void toOtherClients(String msg, SocketChannel toRuleOut) throws IOException {
        for (SelectionKey key : selector.keys()) {
            SelectableChannel client = key.channel();
            if (client instanceof SocketChannel && client != toRuleOut) {
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                ((SocketChannel) client).write(buffer);
            }
        }
    }
}
