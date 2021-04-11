package com.like.netty.protocol.custom.chat;

import com.like.netty.protocol.custom.message.LoginRequestMessage;
import com.like.netty.protocol.custom.message.LoginResponseMessage;
import com.like.netty.protocol.custom.server.service.UserServiceFactory;
import com.like.netty.protocol.custom.server.session.SessionFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import static com.like.netty.protocol.custom.handler.LikeChannelPipeline.*;

/**
 * Create By like On 2021-04-11 14:30
 *
 * @Description: 聊天服务器
 */
public class ChatServer {

    /** chart server port */
    public static final int serverPort = 9999;

    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();

        try {
            ServerBootstrap boot = new ServerBootstrap()
                    .group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(
                            new ChannelInitializer<NioSocketChannel>() {
                                @Override
                                protected void initChannel(NioSocketChannel ch) throws Exception {
                                    ch.pipeline().addLast(getLogHandler());
                                    ch.pipeline().addLast(getLikeProtocolCodecSharable());
                                    ch.pipeline().addLast(getLikeProtocolFrameDecoder());

                                    ch.pipeline().addLast(new SimpleChannelInboundHandler<LoginRequestMessage>() {
                                        @Override
                                        protected void channelRead0(ChannelHandlerContext ctx, LoginRequestMessage msg) throws Exception {
                                            boolean login = UserServiceFactory.getUserService().login(msg.getUsername(), msg.getPassword());
                                            LoginResponseMessage message;
                                            if (login) {
                                                SessionFactory.getSession().bind(ctx.channel(), msg.getUsername());
                                                message = new LoginResponseMessage(true, "登录成功:" + msg.getUsername());
                                            } else {
                                                message = new LoginResponseMessage(false, "用户名或密码不正确");
                                            }
                                            System.out.println(message);
                                            ctx.writeAndFlush(message);
                                        }
                                    });
                                }
                            });
            Channel channel = boot.bind(serverPort).sync().channel();
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }


}
