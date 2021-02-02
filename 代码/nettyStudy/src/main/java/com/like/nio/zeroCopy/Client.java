package com.like.nio.zeroCopy;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * @author like
 * @date 2021-02-02 9:42
 * @contactMe 980650920@qq.com
 * @description
 */
public class Client {
    public static void main(String[] args) {
        try {
            SocketChannel client = SocketChannel.open();
            client.connect(new InetSocketAddress("localhost", 6666));

            String filename = "test.txt";
            FileChannel channel = new FileInputStream(filename).getChannel();
            // 底层是0拷贝
            channel.transferTo(0,channel.size(), client);


            channel.close();
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
