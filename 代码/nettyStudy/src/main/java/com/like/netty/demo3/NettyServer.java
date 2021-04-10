package com.like.netty.demo3;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

/**
 * Create By like On 2021-04-10 11:07
 */
public class NettyServer {

    public static void main(String[] args) {
        // 1.启动器 负责组装netty 组件，启动服务器
        new ServerBootstrap()
                // 2. boosEventLoop workerEventLoop(selector,thread) group 组
                .group(new NioEventLoopGroup())
                // 3. 选择服务器的 serverSocketChannel 实现
                .channel(NioServerSocketChannel.class)
                // 4. boos 负责处理连接 worker(child) 负责处理读写 决定worker 能执行哪些操作
                .childHandler(
                        // 5. channel代表和客户端进行数据读写的通道 initializer 初始化，负责添加别的handler
                        new ChannelInitializer<NioSocketChannel>() {
                            @Override
                            protected void initChannel(NioSocketChannel ch) throws Exception {
                                // 6.添加具体handler
                                ch.pipeline().addLast(new StringDecoder()); // 将ByteBuf 转换为字符串
                                ch.pipeline().addLast(new ChannelInboundHandlerAdapter() { // 自己的自定义handler
                                    @Override
                                    public void channelRead(
                                            ChannelHandlerContext ctx, Object msg) throws Exception {
                                        System.out.println(msg);
                                    }
                                });
                            }
                        })
                // 7.绑定监听端口 8888
                .bind(6666);
    }
}
