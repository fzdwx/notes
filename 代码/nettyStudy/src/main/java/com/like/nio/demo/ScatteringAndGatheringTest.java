package com.like.nio.demo;

import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * @author like
 * @date 2021-01-31 17:14
 * @contactMe 980650920@qq.com
 * @description
 */
public class ScatteringAndGatheringTest {
    private static final int port = 12312;

    public static void main(String[] args) {

        try (ServerSocketChannel server = ServerSocketChannel.open()) {
            InetSocketAddress inetSocketAddress = new InetSocketAddress(port);

            // 1.给server绑定端口
            server.socket().bind(inetSocketAddress);
            // 2.创建缓冲区
            ByteBuffer[] buffers = new ByteBuffer[2];

            buffers[0] = ByteBuffer.allocate(5);
            buffers[1] = ByteBuffer.allocate(3);

            // 3.等待客户端连接
            SocketChannel client = server.accept();
            int messageLength = 8;
            while (true) {

                // a.读取数据
                int read = 0;
                while (read < messageLength) {
                    long l = client.read(buffers);
                    read += l;
                }
                Arrays.asList(buffers).forEach(b->{
                    System.out.println("position:"+ b.position()+"--"+"limit"+b.limit());
                });

                Arrays.asList(buffers).forEach(Buffer::flip);  // 切换读写模式
                // 写入数据
                long write = 0;
                while (write < messageLength) {
                    long l = client.write(buffers);
                    write += l;
                }

                Arrays.asList(buffers).forEach(Buffer::clear);  // clear

                System.out.println("read:" + read + "  " + "write:" + write + "  " + "messageLength:" + messageLength);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
