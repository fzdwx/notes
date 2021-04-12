package com.like.netty.protocol.custom.handler.server;

import com.like.netty.protocol.custom.message.LoginRequestMessage;
import com.like.netty.protocol.custom.message.LoginResponseMessage;
import com.like.netty.protocol.custom.server.service.UserService;
import com.like.netty.protocol.custom.server.service.UserServiceFactory;
import com.like.netty.protocol.custom.server.session.SessionFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 登录请求消息 处理器
 *
 * @author like
 * @date 2021/04/12
 */
@ChannelHandler.Sharable
public class LoginRequestMessageHandler extends SimpleChannelInboundHandler<LoginRequestMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestMessage msg) throws Exception {
        final UserService.LoginStats loginStats = UserServiceFactory.getUserService().login(msg.getUsername(), msg.getPassword());
        LoginResponseMessage message;
        if (loginStats.getStatus()) {
            // 用户登录状态管理:channel连接绑定用户名
            SessionFactory.getSession().bind(ctx.channel(), msg.getUsername());
            message = new LoginResponseMessage(loginStats.getStatus(), loginStats.getType() + msg.getUsername());
        } else {
            message = new LoginResponseMessage(loginStats.getStatus(), loginStats.getType());
        }
        ctx.writeAndFlush(message);
    }
}
