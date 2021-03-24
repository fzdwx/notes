package com.like.netty.http;

import com.like.netty.http.controller.HelloController;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

import java.io.BufferedInputStream;
import java.nio.charset.StandardCharsets;

import static org.springframework.http.HttpHeaders.CONTENT_LENGTH;

/**
 * @author like
 */
public class HttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) {
        if (msg instanceof HttpRequest) {
            System.out.println("msg 类型：" + msg.getClass());

            // 路由分发
            dispatcher(ctx, (HttpRequest) msg);
        }
    }

    /**
     * 调度程序
     * @param ctx
     *         ctx
     * @param request
     *         请求
     */
    private void dispatcher(ChannelHandlerContext ctx, HttpRequest request) {
        String uri = request.uri().substring(1);

        System.out.println("客户端地址：" + ctx.channel().remoteAddress()+"-"+"访问路径："+uri);

        if (uri.equals("favicon.ico")) {
            try {
                BufferedInputStream bis = (BufferedInputStream) this.getClass().getClassLoader().getResourceAsStream("favicon.ico");
                byte[] ico = new byte[1024 * 1024];
                int read = 0;
                while ((read = bis.read(ico)) != -1) {
                    // 读取favicon.ico
                }
                ByteBuf buf = Unpooled.copiedBuffer(ico);
                FullHttpResponse resp = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buf);
                resp.headers().set(CONTENT_LENGTH, buf.readableBytes());

                bis.close();
                buf.clear();
                ctx.writeAndFlush(resp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            doDispatcher(ctx, request, uri);
        }
    }

    /**
     * 做调度器
     * @param ctx
     *         ctx
     * @param request
     * @param uri
     */
    private void doDispatcher(ChannelHandlerContext ctx, HttpRequest request, String uri) {

        if (uri.equals("hello")) {
            String res = new HelloController().hello();
            writeAndReturnJson(ctx, res);
        } else {
            writeAndReturnHtml(ctx, "<h1>欢迎访问</h1>");
        }
        // TODO: 2021/3/24 其他路由请求分发
    }

    /**
     * 写入消息返回
     * @param ctx
     *         ctx
     * @param res
     *         res
     */
    private void writeAndReturnJson(ChannelHandlerContext ctx, String res) {
        // 回复信息给浏览器
        ByteBuf buf = Unpooled.copiedBuffer(res, StandardCharsets.UTF_8);
        FullHttpResponse resp = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buf);
        resp.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json; charset=UTF-8");
        resp.headers().set(CONTENT_LENGTH, buf.readableBytes());

        ctx.writeAndFlush(resp);
    }

    private void writeAndReturnHtml(ChannelHandlerContext ctx, String res) {
        // 回复信息给浏览器
        ByteBuf buf = Unpooled.copiedBuffer(res, StandardCharsets.UTF_8);
        FullHttpResponse resp = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buf);
        resp.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");
        resp.headers().set(CONTENT_LENGTH, buf.readableBytes());

        ctx.writeAndFlush(resp);
    }
}
