package com.like.netty.end;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;

/**
 * Create By like On 2021-04-14 11:03
 */
public class TestBackLog {

    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBoot = new ServerBootstrap()
                    .group(boss, worker)
                    .option(ChannelOption.SO_BACKLOG,100)
                    .handler(new LoggingHandler())
                    .channel(NioServerSocketChannel.class);
            ChannelFuture cf = serverBoot.bind(80).sync();
            cf.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
