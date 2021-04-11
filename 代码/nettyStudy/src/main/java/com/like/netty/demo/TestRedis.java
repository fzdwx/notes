package com.like.netty.demo;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.nio.charset.Charset;

/**
 * Create By like On 2021-04-11 11:53
 * 利用netty 模拟redis 客户端
 */
public class TestRedis {

    static final String LINE = "\r\n";

    public static void main(String[] args) {
        try {
           new Bootstrap()
                    .group(new NioEventLoopGroup())
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                            ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    ByteBuf buf = ctx.alloc().buffer();
                                    write(buf, "*3");
                                    write(buf, "$3");
                                    write(buf, "set");
                                    write(buf, "$4");
                                    write(buf, "name");
                                    write(buf, "$4");    // $ +  发送内容的长度
                                    write(buf, "like");
                                    // set name like
                                    ctx.writeAndFlush(buf);
                                }
                            });
                        }
                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                            ByteBuf buf = (ByteBuf) msg;
                            System.out.println(buf.toString(Charset.defaultCharset()));
                        }
                    })
                    .connect("localhost", 6379);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void write(ByteBuf buf, String value) {
        buf.writeBytes(value.getBytes());
        buf.writeBytes(LINE.getBytes());
    }
}
