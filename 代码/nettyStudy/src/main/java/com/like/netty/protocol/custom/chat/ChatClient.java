package com.like.netty.protocol.custom.chat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Create By like On 2021-04-11 15:49
 * 聊天客户端
 */
public class ChatClient {

    public static void main(String[] args) {
        final NioEventLoopGroup boss = new NioEventLoopGroup();
        try {
            Channel ch = new Bootstrap()
                    .group(boss)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {

                                }
                            });
                        }
                    })
                    .connect("localhost", ChatServer.serverPort)
                    .sync()
                    .channel();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            boss.shutdownGracefully();
        }
    }
}
