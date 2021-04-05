package com.like.netty.c1;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import static com.like.netty.util.ByteBufferUtil.debugAll;

/**
 * Create By Like On 2021-04-05 20:20
 * @Description: 粘包和半包
 */
public class TestNianBaoAdndBanBao {
    /*
    Hello , netty\n
    I'm like\n
    How are you?\n
    变成了下面的两个 byteBuffer(黏包，半包)
    Hello , world\nI 'm like\n Ho
    w are you?\n
    */
    public static void main(String[] args) {
        ByteBuffer source = ByteBuffer.allocate(1024);
        String msg1 = "Hello , netty\nI 'm like\n Ho";
        String msg2 = "w are you?\n";

        source.put(msg1.getBytes());
        split(source);
        source.put(msg2.getBytes());
        split(source);
    }

    private static void split(ByteBuffer source) {
        // 切换模式
        source.flip();
        for (int i = 0; i < source.limit(); i++) {
            if (source.get(i) == '\n') {
                // 保证新生成的buffer大于要读取的长度
                int length = i + 1 - source.position();
                ByteBuffer target = ByteBuffer.allocate(length);
                // 读取source中[0,length)长度的数据到target
                for (int j = 0; j < length; j++) {
                    target.put(source.get());

                }
                target.flip();
                System.out.println((StandardCharsets.UTF_8.decode(target)));
                debugAll(target);
            }
        }
        // 上次未读取的位置开始
        source.compact();
    }
}
