package com.like.netty.protocol.custom.handler;

import cn.hutool.core.util.ObjectUtil;
import com.like.netty.protocol.custom.message.PingMessage;
import com.like.netty.protocol.custom.message.PongMessage;
import com.like.netty.protocol.custom.server.session.Session;
import com.like.netty.protocol.custom.server.session.factory.SessionFactory;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicReference;

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

    private AtomicReference<String> username;

    public HeatBeatPongMessageHandler(AtomicReference<String> username) {
        this.username = username;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        final IdleStateEvent event = (IdleStateEvent) evt;
        if (event.state().equals(IdleState.WRITER_IDLE)) {
            final Session session = SessionFactory.getSession();
            Channel channel = session.getChannel(username.get());

            System.out.println("channel = " + channel);
            if (ObjectUtil.isNotNull(channel)) {
                // TODO: 2021/4/13  内存实现为null
                channel.writeAndFlush(new PingMessage("自己写个自己"));
                System.out.println("SessionFactory.getSession().getUserName(channel) = " + SessionFactory.getSession().getUserName(channel));
            }
            ctx.writeAndFlush(new PongMessage("pong-1"));
            log.info("#userEventTriggered(..):{} 心跳数据包发送-pong", username.get());
        }
    }
}
