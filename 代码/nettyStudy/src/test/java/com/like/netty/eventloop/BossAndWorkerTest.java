package com.like.netty.eventloop;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Create By like On 2021-04-10 12:55
 */
public class BossAndWorkerTest {
    private final static Logger log = LoggerFactory.getLogger(BossAndWorkerTest.class);

    public static void main(String[] args) {

        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();

        DefaultEventLoopGroup defaultGroup = new DefaultEventLoopGroup();
        new ServerBootstrap()
                .group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(
                        new ChannelInitializer<NioSocketChannel>() {
                            @Override
                            protected void initChannel(NioSocketChannel ch) throws Exception {
                                ch.pipeline().addLast(new StringDecoder());
                                ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                                    @Override
                                    public void channelRead(
                                            ChannelHandlerContext ctx, Object msg) throws Exception {
                                        log.info("#channelRead(..): {}", msg);
                                    }
                                });
                                // 可以在添加一個group
                                ch.pipeline().addLast(defaultGroup, new ChannelInboundHandlerAdapter() {
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        log.info("#channelRead(..):{}",msg);
                                    }
                                });
                            }
                        })
                .bind(8888);
    }
}
