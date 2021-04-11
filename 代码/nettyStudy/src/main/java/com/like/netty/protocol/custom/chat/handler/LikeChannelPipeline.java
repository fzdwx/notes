package com.like.netty.protocol.custom.chat.handler;

import com.like.netty.protocol.custom.chat.protocol.MessageCodec;
import com.like.netty.protocol.custom.chat.protocol.MessageCodecSharable;
import com.sun.istack.internal.NotNull;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * Create By like On 2021-04-11 14:47
 * 协议必须的 pipLine
 */
public class LikeChannelPipeline {
    /** like 协议编解码器 */
    public static final MessageCodec likeProtocolCodec = new MessageCodec();
    public static final MessageCodecSharable likeProtocolCodecSharable = new MessageCodecSharable();

    /**
     * netty日志处理器
     * order 1
     *
     * @return {@link LoggingHandler}
     */
    public static LoggingHandler getLogHandler() {
        return new LoggingHandler(LogLevel.DEBUG);
    }

    /**
     * like 协议编解码器
     * 协议主要实现
     * order 2
     *
     * @return {@link MessageCodec}
     */
    public static MessageCodec getLikeProtocolCodec() {
        return likeProtocolCodec;
    }

    /**
     * like 协议编解码器 安全的
     * 协议主要实现
     * order 2
     *
     * @return {@link MessageCodec}
     */
    public static MessageCodecSharable getLikeProtocolCodecSharable() {
        return likeProtocolCodecSharable;
    }

    /**
     * like 协议帧解码器
     * 不能被多线程共享
     * order 3
     *
     * @return {@link LengthFieldBasedFrameDecoder}
     */
    @NotNull
    public static LengthFieldBasedFrameDecoder getLikeProtocolFrameDecoder() {
        return new LengthFieldBasedFrameDecoder(1024, 18, 4, 0, 0);
    }
}
