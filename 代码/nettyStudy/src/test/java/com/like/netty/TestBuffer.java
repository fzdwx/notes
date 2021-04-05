package com.like.netty;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Create By Like On 2021-04-05 17:12
 *
 * @Description: buffer的初步使用
 */
@Slf4j
public class TestBuffer {

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
}
