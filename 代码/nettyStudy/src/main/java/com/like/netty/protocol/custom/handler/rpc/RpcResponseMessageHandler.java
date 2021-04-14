package com.like.netty.protocol.custom.handler.rpc;

import com.like.netty.protocol.custom.message.rpc.RpcResponseMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Create By like On 2021-04-14 15:23
 * <p>
 * rpc 响应消息处理
 */
public class RpcResponseMessageHandler extends SimpleChannelInboundHandler<RpcResponseMessage> {
    private final static Logger log = LoggerFactory.getLogger(RpcResponseMessageHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponseMessage msg) throws Exception {
        log.info("#channelRead0(..):rpc 调用{} ",msg.getReturnValue());
    }
}
