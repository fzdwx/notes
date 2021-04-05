package com.like.old.nio.zeroCopy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author like
 * @date 2021-02-02 9:38
 * @contactMe 980650920@qq.com
 * @description
 */
public class Server {

    public static void main(String[] args) {
        try {
            ServerSocketChannel server = ServerSocketChannel.open();
            server.bind(new InetSocketAddress(6666));

            ByteBuffer buffer = ByteBuffer.allocate(1024);

            while (true) {
                SocketChannel client = server.accept();
                int read =0;
                while (read != -1) {
                    read = client.read(buffer);
                }

                buffer.rewind(); // 倒带 position = 0   mart作废
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
