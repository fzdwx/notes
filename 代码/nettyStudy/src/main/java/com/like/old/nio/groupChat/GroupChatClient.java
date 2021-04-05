package com.like.old.nio.groupChat;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @author like
 * @date 2021-02-01 18:54
 * @contactMe 980650920@qq.com
 * @description
 */
@Slf4j
public class GroupChatClient {
    public static final String HOST = "localhost";
    public static final int PORT = 8888;
    private Selector selector;
    private SocketChannel client;
    private String userName;
    public GroupChatClient() {
        try {
            client = SocketChannel.open(new InetSocketAddress(HOST, PORT));
            selector = Selector.open();

            client.configureBlocking(false);
            client.register(selector, SelectionKey.OP_READ);

            System.out.println("请输入用户名:");
            Scanner sc = new Scanner(System.in);
            userName = sc.nextLine();
            log.info(userName + " is ok !");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        start();
    }

    public static GroupChatClient start() {
        GroupChatClient client = new GroupChatClient();

        new Thread(() -> {
            while (true) {
                client.readMsg();
            }
        }, "readMsg").start();
        // 2.客户端发送数据
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入你要发送的信息：");
        while (sc.hasNext()) {
            String msg = sc.nextLine();
            client.sendMsg(msg);
        }

        return client;
    }

    private void readMsg() {
        try {
            int readChannels = selector.select();
            if (readChannels > 0) {
                Iterator<SelectionKey> iKeys = selector.selectedKeys().iterator();
                while (iKeys.hasNext()) {
                    SelectionKey key = iKeys.next();
                    if (key.isReadable()) {
                        SocketChannel server = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        int read = server.read(buffer);
                        if (read > 0) {
                            System.out.println(new String(buffer.array()));
                        }
                    }
                    iKeys.remove();  // 防止重复操作
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMsg(String msg) {
        msg = userName + "说:" + msg;
        try {
            client.write(ByteBuffer.wrap(msg.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
