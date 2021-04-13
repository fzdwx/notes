package com.like.netty.protocol.custom.handler;

import cn.hutool.core.util.ObjectUtil;
import com.like.netty.protocol.custom.message.PingMessage;
import com.like.netty.protocol.custom.server.session.SessionFactory;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Create By like On 2021-04-13 15:09
 * <p>
 * 心跳消息 发送 ping
 * extends {@link ChannelDuplexHandler} 双向通道
 *
 * @see LikeChannelMustPipeline#getIdleReadStateHandler
 */
public class HeatBeatPingMessageHandler extends ChannelDuplexHandler {  // 双向通道

    private final static Logger log = LoggerFactory.getLogger(HeatBeatPingMessageHandler.class);

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        final IdleStateEvent event = (IdleStateEvent) evt;
        if (event.state().equals(IdleState.READER_IDLE)) {
            final String userName = SessionFactory.getSession().getUserName(ctx.channel());
            final Channel channel = SessionFactory.getSession().getChannel(userName);
            if (ObjectUtil.isNotNull(channel)) {
                channel.writeAndFlush(new PingMessage("ping-0"));
            }
            log.info("#userEventTriggered(..): 心跳检测：{}", userName);
        }
    }
}
