package com.like.nio.deno2;

import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * @author like
 * @date 2021-02-01 13:18
 * @contactMe 980650920@qq.com
 * @description
 */
@Slf4j
public class NIOClient {
    public static void main(String[] args) {
        try (SocketChannel client = SocketChannel.open()) {
            // 1.设置为非阻塞式
            client.configureBlocking(false);
            // 2.设置服务端ip地址并连接
            if (!client.connect(new InetSocketAddress("localhost", 6666))) {
                while (!client.finishConnect()) {
                    log.info("正在等待连接中，请等待···");
                }
            }
            // 3.连接成功，发送数据
            Scanner input = new Scanner(System.in);
            String userInput = "";
            ByteBuffer buffer = null;
            while (true) {
                log.info("请输入你要发送的信息：");

                userInput = input.nextLine();
                buffer = ByteBuffer.wrap(userInput.getBytes(), 0, userInput.length());
                client.write(buffer);
                buffer.clear();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
