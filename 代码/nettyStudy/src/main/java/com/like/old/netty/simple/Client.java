package com.like.old.netty.simple;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author like
 * @date 2021-02-02 16:25
 * @contactMe 980650920@qq.com
 * @description
 */
@Slf4j
public class Client {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ClientHandler());
                        }
                    });

            ChannelFuture cf = bootstrap.connect("localhost", Server.serverPort).sync();
            log.info("客户端启动成功");

            cf.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }
}
