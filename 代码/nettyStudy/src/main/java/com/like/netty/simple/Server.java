package com.like.netty.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author like
 * @date 2021-02-02 15:24
 * @contactMe 980650920@qq.com
 * @description
 */
@Slf4j
public class Server {

    public static final int serverPort = 6666;

    public static void main(String[] args) throws InterruptedException {
        // 1.创建bossGroup workerGroup
        // a.创建了2个线程组
        // b.boss只处理连接请求，worker是真正进行业务处理
        // c.两个都是无限循环
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // 2.创建服务器的启动对象 配置参数
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup) // 设置2个线程组
                    .channel(NioServerSocketChannel.class) // 使用这个类作为通道
                    .option(ChannelOption.SO_BACKLOG, 128)  // 设置线程队列等待连接个数
                    .childOption(ChannelOption.SO_KEEPALIVE, true) // 设置保持活动连接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ServerHandler());  // 绑定我们自己的handler
                        }
                    });// 给worker中的 eventLoop对应的管道设置处理器
            log.info("server init is ok");

            // 3.绑定一个端口并且同步，生成一个cf对象
            ChannelFuture cf = bootstrap.bind(serverPort).sync();
            log.info("server port(" + serverPort + ") bind success,server start!");

            // 4.监听通道关闭
            cf.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
