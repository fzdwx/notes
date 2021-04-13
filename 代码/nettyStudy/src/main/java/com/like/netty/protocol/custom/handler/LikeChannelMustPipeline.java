package com.like.netty.protocol.custom.handler;

import com.like.netty.protocol.custom.message.protocol.MessageCodec;
import com.like.netty.protocol.custom.message.protocol.MessageCodecSharable;
import com.sun.istack.internal.NotNull;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * Create By like On 2021-04-11 14:47
 * 协议必须的 pipLine
 *
 * @see this#getLogHandler()
 * @see this#getLikeProtocolCodecSharable()
 * @see this#getLikeProtocolFrameDecoder()
 */
public class LikeChannelMustPipeline {
    /** 日志处理handler */
    private static final LoggingHandler loggingHandler = new LoggingHandler(LogLevel.DEBUG);
    /** like 协议编解码器可分享的 */
    private static final MessageCodecSharable likeProtocolCodecSharable = new MessageCodecSharable();

    /**
     * netty日志处理器 default level debug
     * order 1
     *
     * @return {@link LoggingHandler}
     */
    public static LoggingHandler getLogHandler() {
        return loggingHandler;
    }

    /**
     * like 协议编解码器 安全的
     * 协议主要实现
     * order 2
     * MessageCodecSharable extents {@link MessageCodec}
     *
     * @return {@link MessageCodecSharable}
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
        return new LikeProtocolFrameDecoder();
    }

    /**
     * like 协议帧解码器
     */
    static class LikeProtocolFrameDecoder extends LengthFieldBasedFrameDecoder {
        public LikeProtocolFrameDecoder() {
            this(1024, 18, 4, 0, 0);
        }

        public LikeProtocolFrameDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
            super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
        }
    }
}
