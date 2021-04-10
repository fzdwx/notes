package com.like.netty.byteBuf;

import cn.hutool.core.text.StrBuilder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.nio.charset.Charset;

/**
 * Create By like On 2021-04-10 18:33
 */
public class ByteBufTest {

    public static void main(String[] args) {
        ByteBuf hBuf = ByteBufAllocator.DEFAULT.heapBuffer();  // 堆内存
        ByteBuf dBuf = ByteBufAllocator.DEFAULT.directBuffer(); // 直接内存
        t1();
    }

    private static void t1() {
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();

        System.out.println(buf);
        StrBuilder sb = new StrBuilder();
        for (int i = 1; i < 300; i++) {
            sb.append("a");
        }

        buf.writeBytes(sb.toString().getBytes());
        System.out.println(buf.writerIndex());
        System.out.println(buf);
        System.out.println(buf.toString(Charset.defaultCharset()));
    }
}
