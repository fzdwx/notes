package com.like.netty.protocol.custom.rpc;

import com.like.netty.protocol.custom.chat.ChatServer;
import com.like.netty.protocol.custom.config.Config;
import com.like.netty.protocol.custom.handler.rpc.RpcRequestMessageHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;

import static com.like.netty.protocol.custom.handler.LikeChannelMustPipeline.*;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Create By like On 2021-04-14 15:15
 */
public class RpcServer {

    private final static Logger log = getLogger(ChatServer.class);

    /** chart server port */
    public static final int serverPort = Integer.parseInt(Config.getServerPort());
    public static final String serverHost = Config.getServerHost();

    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            ServerBootstrap boot = new ServerBootstrap()
                    .group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(
                            new ChannelInitializer<NioSocketChannel>() {
                                @Override
                                protected void initChannel(NioSocketChannel ch) throws Exception {
                                    ch.pipeline().addLast(getLogHandler());
                                    ch.pipeline().addLast(getLikeProtocolCodecSharable());
                                    ch.pipeline().addLast(getLikeProtocolFrameDecoder());

                                    ch.pipeline().addLast(new RpcRequestMessageHandler());
                                }
                            });
            Channel channel = boot.bind(serverPort).sync().channel();
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
