package com.like.netty.protocol.custom.handler;

import com.like.netty.protocol.custom.message.PongMessage;
import com.like.netty.protocol.custom.server.session.SessionFactory;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Create By like On 2021-04-13 15:04
 * <p>
 * 心跳消息 发送 pong
 * extends {@link ChannelDuplexHandler} 双向通道
 *
 * @see LikeChannelMustPipeline#getIdleWriteStateHandler
 */
public class HeatBeatPongMessageHandler extends ChannelDuplexHandler {

    private final static Logger log = LoggerFactory.getLogger(HeatBeatPongMessageHandler.class);

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        final IdleStateEvent event = (IdleStateEvent) evt;
        if (event.state().equals(IdleState.WRITER_IDLE)) {
            final String userName = SessionFactory.getSession().getUserName(ctx.channel());
            ctx.writeAndFlush(new PongMessage("pong-1"));
            log.info("#userEventTriggered(..):{} 心跳数据包发送-pong", userName);
        }
    }
}
