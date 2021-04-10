package com.like.netty.demo3;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

/**
 * Create By like On 2021-04-10 11:27
 */
public class NettyClient {

    public static void main(String[] args) {
        try {
            new Bootstrap()
                    .group(new NioEventLoopGroup())
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new StringEncoder());
                        }
                    })
                    .connect("localhost", 6666)
                    .sync()
                    .channel()
                    .writeAndFlush("hello netty server");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
