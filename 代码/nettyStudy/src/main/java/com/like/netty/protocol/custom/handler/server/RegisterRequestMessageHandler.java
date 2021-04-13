package com.like.netty.protocol.custom.handler.server;

import com.like.netty.protocol.custom.message.RegisterRequestMessage;
import com.like.netty.protocol.custom.message.RegisterResponseMessage;
import com.like.netty.protocol.custom.server.service.UserServiceFactory;
import com.like.netty.protocol.custom.server.session.SessionFactory;
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
            res = "注册失败，用户名已经存";
        }
        ctx.writeAndFlush(new RegisterResponseMessage(register,res));
    }
}
