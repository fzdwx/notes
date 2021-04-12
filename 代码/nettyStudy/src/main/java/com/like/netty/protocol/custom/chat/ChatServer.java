package com.like.netty.protocol.custom.chat;

import com.like.netty.protocol.custom.handler.server.ChatRequestMessageHandler;
import com.like.netty.protocol.custom.handler.server.GroupCreateRequestMessageHandler;
import com.like.netty.protocol.custom.handler.server.LoginRequestMessageHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import static com.like.netty.protocol.custom.handler.LikeChannelMustPipeline.getLikeProtocolCodecSharable;
import static com.like.netty.protocol.custom.handler.LikeChannelMustPipeline.getLikeProtocolFrameDecoder;

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
                                    // ch.pipeline().addLast(getLogHandler());
                                    ch.pipeline().addLast(getLikeProtocolCodecSharable());
                                    ch.pipeline().addLast(getLikeProtocolFrameDecoder());

                                    ch.pipeline().addLast(new LoginRequestMessageHandler());  // 登录消息处理器
                                    ch.pipeline().addLast(new ChatRequestMessageHandler());  // 私聊消息处理器
                                    ch.pipeline().addLast(new GroupCreateRequestMessageHandler());  // 创建群聊处理器
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
