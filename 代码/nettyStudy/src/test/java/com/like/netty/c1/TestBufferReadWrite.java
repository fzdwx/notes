package com.like.netty.c1;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import static com.like.netty.util.ByteBufferUtil.debugAll;

/**
 * Create By Like On 2021-04-05 18:04
 */
public class TestBufferReadWrite {

    public static void main(String[] args) {
        ByteBuffer b1 = ByteBuffer.allocate(5);
        ByteBuffer b2 = ByteBuffer.allocateDirect(10);
        b1.put(((byte) (0x61)));
        b2.put(((byte) (0x61)));
        debugAll(b1);
        debugAll(b2);

        System.out.println(b2.getClass());
        System.out.println(b1.getClass());

        ByteBuffer b3 = ByteBuffer.allocateDirect(10);
        ByteBuffer b = StandardCharsets.UTF_8.encode("hello");
        
        String str = StandardCharsets.UTF_8.decode(b3).toString();

    }
}
