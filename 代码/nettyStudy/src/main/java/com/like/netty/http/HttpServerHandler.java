package com.like.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import sun.nio.cs.ext.GBK;

import static org.springframework.http.HttpHeaders.CONTENT_LENGTH;

/**
 * @author like
 * @date 2021-02-03 18:24
 * @contactMe 980650920@qq.com
 * @description
 */
public class HttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        if (msg instanceof HttpRequest) {
            System.out.println("msg 类型：" + msg.getClass());
            System.out.println("客户端地址：" + ctx.channel().remoteAddress());

            // 回复信息给浏览器
            ByteBuf buf = Unpooled.copiedBuffer("服务器已经搜到消息", GBK.defaultCharset());

            FullHttpResponse resp = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buf);
            resp.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            resp.headers().set(CONTENT_LENGTH, buf.readableBytes());

            ctx.writeAndFlush(resp);
        }
    }
}
