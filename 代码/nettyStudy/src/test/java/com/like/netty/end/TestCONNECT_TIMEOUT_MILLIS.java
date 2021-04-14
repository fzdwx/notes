package com.like.netty.end;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;

/**
 * Create By like On 2021-04-14 10:22
 */
public class TestCONNECT_TIMEOUT_MILLIS {

    public static void main(String[] args) {

        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBoot = new ServerBootstrap()
                    .group(boss, worker)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 300)  // 300毫秒 socketChannel
                    .childOption(ChannelOption.CONNECT_TIMEOUT_MILLIS, 300) // serverSocketChannel
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
