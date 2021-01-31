package com.like.nio;

import java.nio.IntBuffer;
import java.util.Random;

/**
 * @author like
 * @date 2021-01-31 12:56
 * @contactMe 980650920@qq.com
 * @description buffer的简单使用
 */
public class BasicBuffer {

    public static void main(String[] args) {
        // 1.创建一个buffer，可以存放5个int类型的数据
        IntBuffer buffer = IntBuffer.allocate(5);
        // 2.向buffer中存放数据
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put(new Random().nextInt(10));
        }

        buffer.flip(); // 切换模式 读/写

        // 3.输出
        while (buffer.hasRemaining()) {
            System.out.println(buffer.get());
        }

    }
}
