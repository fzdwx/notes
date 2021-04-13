package com.like.netty.protocol.custom.handler.server;

import com.like.netty.protocol.custom.server.session.SessionFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Create By like On 2021-04-13 13:44
 * <p>
 * 退出群聊服务器 -> 打印 用户离线：like
 */
@ChannelHandler.Sharable
public class QuitHandler extends ChannelInboundHandlerAdapter {

    private final static Logger log = getLogger(QuitHandler.class);

    /**
     * channel 正常断开触发
     *
     * @param ctx ctx
     * @throws Exception 异常
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        final String username = SessionFactory.getSession().unbind(ctx.channel());

        log.info("#channelInactive(..): 用户离线：{}", username);
    }

    /**
     *  channel 异常断开触发
     *
     * @param ctx   ctx
     * @param cause 导致
     * @throws Exception 异常
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        final String username = SessionFactory.getSession().unbind(ctx.channel());

        log.info("#channelInactive(..): 用户异常离线：{}，cause:{}", username, cause);
    }
}
