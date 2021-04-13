package com.like.netty.demo3;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * Create By like On 2021-04-11 11:39
 */
public class TestLengthFiledDecoder {

    public static void main(String[] args) {
        EmbeddedChannel lch = new EmbeddedChannel(
                new LengthFieldBasedFrameDecoder(1024,
                        0,          // 长度字节从0开始
                        4,         // 长度字节占4个
                        1,        // 因为添加了版本号，版本号占一个字节 所以跳过一个字节
                        0),      // 剥离前5个字节
                new LoggingHandler(LogLevel.DEBUG)
        );

        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();

        writeInBuf(buf,  "Hello,world");
        writeInBuf(buf,  "Hi!");
        writeInBuf(buf, "this is server");

        lch.writeInbound(buf);
    }

    private static void writeInBuf(ByteBuf buf, String content) {
        byte[] bytes = content.getBytes();
        int length = bytes.length;

        buf.writeInt(length);    // 前4个字节表示长度
        buf.writeByte(1); // 第5个字节表示版本号
        buf.writeBytes(bytes); // 写入的内容
    }
}
