package com.like.netty.protocol.custom.protocol;

import com.like.netty.protocol.custom.message.LoginRequestMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;
import org.jetbrains.annotations.NotNull;

/**
 * Create By like On 2021-04-11 13:16
 */
class MessageCodecTest {

    public static void main(String[] args) throws Exception {
        LengthFieldBasedFrameDecoder LIKEPROTOCALFrameDecoder = getLikeProtocolFrameDecoder();
        EmbeddedChannel test = new EmbeddedChannel(
                new LoggingHandler(),
                LIKEPROTOCALFrameDecoder
                ,new MessageCodec());

        // test encode
        LoginRequestMessage msg = new LoginRequestMessage("like", "123");
        // test.writeOutbound(msg);

        // test decode
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        new MessageCodec().encode(null, msg, buf);
        ByteBuf s1 = buf.slice(0, 100);
        ByteBuf s2 = buf.slice(100, buf.readableBytes() - 100);
        // test.writeInbound(buf);
        s1.retain(); // 引用计数
        test.writeInbound(s1);
        test.writeInbound(s2);
    }

    /**
     * like 协议帧解码器
     * 不能被多线程共享
     * @return {@link LengthFieldBasedFrameDecoder}
     */
    @NotNull
    private static LengthFieldBasedFrameDecoder getLikeProtocolFrameDecoder() {
        return new LengthFieldBasedFrameDecoder(1024, 18, 4, 0, 0);
    }
}