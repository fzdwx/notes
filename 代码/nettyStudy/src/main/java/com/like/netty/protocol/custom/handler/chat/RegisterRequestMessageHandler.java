package com.like.netty.protocol.custom.handler.chat;

import com.like.netty.protocol.custom.message.chat.RegisterRequestMessage;
import com.like.netty.protocol.custom.message.chat.RegisterResponseMessage;
import com.like.netty.protocol.custom.server.service.factory.UserServiceFactory;
import com.like.netty.protocol.custom.server.session.factory.SessionFactory;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Create By like On 2021-04-12 21:52
 * 注册消息
 */
public class RegisterRequestMessageHandler extends SimpleChannelInboundHandler<RegisterRequestMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RegisterRequestMessage msg) throws Exception {
        final boolean register = UserServiceFactory.getUserService().register(msg.getUsername(), msg.getPassword());
        String res;
        if (register) {
            // 用户登录状态管理:channel连接绑定用户名
            SessionFactory.getSession().bind(ctx.channel(), msg.getUsername());
            res = "注册成功";
        } else {
            res = "注册失败，用户名已经存在";
        }
        ctx.writeAndFlush(new RegisterResponseMessage(register,res));
    }
}
