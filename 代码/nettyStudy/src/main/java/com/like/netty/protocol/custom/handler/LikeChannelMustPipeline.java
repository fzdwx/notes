package com.like.netty.protocol.custom.handler;

import com.like.netty.protocol.custom.config.Config;
import com.like.netty.protocol.custom.message.protocol.MessageCodec;
import com.like.netty.protocol.custom.message.protocol.MessageCodecSharable;
import com.sun.istack.internal.NotNull;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

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
    /** 安全的 like 协议编解码器 */
    private final static MessageCodecSharable likeProtocolCodecSharable = new MessageCodecSharable((Config.getMessageSerializer()));


    /**
     * netty日志处理器 default level debug
     *
     * @return {@link LoggingHandler}
     */
    public static LoggingHandler getLogHandler() {
        return loggingHandler;
    }

    /**
     * 安全的 like 协议编解码器
     * 协议主要实现
     * <p>
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
     *
     * @return {@link LengthFieldBasedFrameDecoder}
     */
    @NotNull
    public static LengthFieldBasedFrameDecoder getLikeProtocolFrameDecoder() {
        return new LikeProtocolFrameDecoder();
    }

    /**
     * 空闲处理器 - 读取状态
     * <p>
     * 设置为 readerIdleTimeSeconds s内未读取到数据就触发
     *
     * @return {@link IdleStateHandler}
     */
    public static IdleStateHandler getIdleReadStateHandler() {
        return new LikeIdleStateHandler(15, 0, 0);
    }

    /**
     * 空闲处理器 - 写状态
     * <p>
     * 设置为 writerIdleTimeSeconds s未写入数据触发
     *
     * @return {@link IdleStateHandler}
     */
    public static IdleStateHandler getIdleWriteStateHandler() {
        return new LikeIdleStateHandler(0, 10, 0);
    }

    /**
     * like 协议帧解码器
     * </p>
     * 构造参数对应 {@link MessageCodec}
     */
    static class LikeProtocolFrameDecoder extends LengthFieldBasedFrameDecoder {
        public LikeProtocolFrameDecoder() {
            this(1024, 18, 4, 0, 0);
        }

        public LikeProtocolFrameDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
            super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
        }
    }

    /**
     * like 空闲状态处理程序
     */
    private static class LikeIdleStateHandler extends IdleStateHandler {

        public LikeIdleStateHandler(int readerIdleTimeSeconds, int writerIdleTimeSeconds, int allIdleTimeSeconds) {
            super(readerIdleTimeSeconds, writerIdleTimeSeconds, allIdleTimeSeconds);
        }
    }
}
