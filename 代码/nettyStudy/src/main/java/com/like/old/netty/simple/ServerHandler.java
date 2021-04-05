package com.like.old.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * @author like
 * @date 2021-02-02 15:46
 * @contactMe 980650920@qq.com
 * @description 1.自定义一个handler 需要继续netty规定好的某个handlerAdapter
 */
@Slf4j
public class ServerHandler extends ChannelInboundHandlerAdapter {

    public static final String systemInfo = "系统消息：";

    /**
     * 读取客户端发送的消息
     *
     * @param ctx 上下文对象，含有管道pipeline,通道channel，地址
     * @param msg 消息
     * @throws Exception 异常
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        // 1.假设我们有一个非常耗时间的业务 -> 异步执行 -> 提交channel对应的NIOEventLoop的taskQueue
//        // - 解决方案：用户程序自定义的普通程序
//        ctx.channel().eventLoop().execute(()->{
//            try {
//                TimeUnit.SECONDS.sleep(10); // 复杂业务
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
        // -解决方案 -> 将复杂业务提交的scheduleTaskQueue
        ctx.channel().eventLoop().schedule(() ->{
            try {
                TimeUnit.SECONDS.sleep(10); // 复杂业务

            } catch (Exception e) {
                e.printStackTrace();
            }
        },1,TimeUnit.SECONDS);

        ByteBuf buf = (ByteBuf) msg;
        System.out.println("收到客户端的消息：" + buf.toString(StandardCharsets.UTF_8));
        log.info("客户端地址：" + ctx.channel().remoteAddress());
    }

    /**
     * 数据读取完毕
     *
     * @param ctx 上下文对象
     * @throws Exception 异常
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // 将数据写入缓存并刷新
        ctx.writeAndFlush(Unpooled.copiedBuffer(systemInfo + "服务器已经收到消息", StandardCharsets.UTF_8));
    }

    /**
     * 处理异常，一般是关闭通道
     *
     * @param ctx   ctx
     * @param cause 导致
     * @throws Exception 异常
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
