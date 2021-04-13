package com.like.netty.protocol.custom.handler.server;

import com.like.netty.protocol.custom.message.chat.ChatRequestMessage;
import com.like.netty.protocol.custom.message.chat.ChatResponseMessage;
import com.like.netty.protocol.custom.server.session.SessionFactory;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Objects;

/**
 * Create By like On 2021-04-12 19:48
 * <p>
 * 私聊 聊天信息处理器
 * from -> to
 * @see ChatRequestMessage   私聊消息请求
 * @see ChatResponseMessage  私聊消息响应
 */
@ChannelHandler.Sharable
public class ChatRequestMessageHandler extends SimpleChannelInboundHandler<ChatRequestMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChatRequestMessage msg) throws Exception {
        final String from = msg.getFrom();
        final String to = msg.getTo();
        final String content = msg.getContent();
        final Channel toChannel = SessionFactory.getSession().getChannel(to);
        final Channel fromChannel = SessionFactory.getSession().getChannel(from);
        if (Objects.isNull(toChannel)) {
            // 不在线
            fromChannel.writeAndFlush(new ChatResponseMessage(false, "对方用户不在线或不存在"));
        } else {
            // 在线
            toChannel.writeAndFlush(new ChatResponseMessage(from, content));
        }

    }
}
