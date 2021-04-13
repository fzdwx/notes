package com.like.netty.protocol.test;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Create By like On 2021-04-11 12:16
 */
public class TestHttp {

    private final static Logger log = getLogger(TestHttp.class);

    public static void main(String[] args) {

        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBoot = new ServerBootstrap()
                    .group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(
                            new ChannelInitializer<NioSocketChannel>() {
                                @Override
                                protected void initChannel(NioSocketChannel ch) throws Exception {
                                    ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                                    ch.pipeline().addLast(new HttpServerCodec()); // http 编解码器
                                    ch.pipeline().addLast(new SimpleChannelInboundHandler<HttpRequest>() {
                                        @Override
                                        protected void channelRead0(ChannelHandlerContext ctx, HttpRequest msg) throws Exception {
                                            // 获取请求的信息
                                            log.info("#channelRead0(..): uri: {}", msg.uri());

                                            // 返回响应信息                                               http协议版本           响应状态
                                            DefaultFullHttpResponse resp = new DefaultFullHttpResponse(msg.protocolVersion(), HttpResponseStatus.OK);
                                            byte[] content = "<h1>hello my web</h1>".getBytes();
                                            resp.content().writeBytes(content);         // 响应信息
                                            resp.headers().add(HttpHeaderNames.CONTENT_LENGTH, content.length);   // 请求头  响应长度

                                            // 写入channel
                                            ctx.writeAndFlush(resp);
                                        }
                                    });
                                    ch.pipeline().addLast(new SimpleChannelInboundHandler<HttpContent>() {
                                        @Override
                                        protected void channelRead0(ChannelHandlerContext ctx, HttpContent msg) throws Exception {
                                            log.info("#channelRead0(..): HttpContent: {}", msg);
                                        }
                                    });
                                   /* ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                                        @Override
                                        public void channelRead(
                                                ChannelHandlerContext ctx, Object msg) throws Exception {
                                            log.info("#channelRead(..): msg classs :{}", msg.getClass());
                                            // msg classs :class io.netty.handler.codec.http.DefaultHttpRequest
                                            // msg classs :class io.netty.handler.codec.http.LastHttpContent$1
                                            if (msg instanceof HttpRequest) {

                                            } else if (msg instanceof HttpContent) {

                                            } else {

                                            }
                                        }
                                    });*/

                                }
                            });
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
