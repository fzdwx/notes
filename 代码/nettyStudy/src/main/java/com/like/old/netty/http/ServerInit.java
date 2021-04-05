package com.like.old.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author like
 * @date 2021-02-03 18:26
 * @contactMe 980650920@qq.com
 * @description
 */
public class ServerInit extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        // 1.加入编解码器 codec
        // netty 提高的处理http的编解码器
        pipeline.addLast(new HttpServerCodec());
        // 2.自定义的handler
        pipeline.addLast(new HttpServerHandler());
    }
}
