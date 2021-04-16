package com.like.netty.protocol.custom.rpc;

import com.like.netty.protocol.custom.handler.rpc.RpcResponseMessageHandler;
import com.like.netty.protocol.custom.message.rpc.RpcRequestMessage;
import com.like.netty.protocol.custom.server.service.HelloService;
import com.like.netty.protocol.custom.util.SqeIdGetter;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.DefaultPromise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

import static com.like.netty.protocol.custom.handler.LikeChannelMustPipeline.getLikeProtocolCodecSharable;
import static com.like.netty.protocol.custom.handler.LikeChannelMustPipeline.getLikeProtocolFrameDecoder;

/**
 * Create By like On 2021-04-14 15:18
 * 封装后的channel
 */
public class RpcBetterClient {

    private final static Logger log = LoggerFactory.getLogger(RpcBetterClient.class);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        HelloService hello = getProxyService(HelloService.class);
        System.out.println(hello.hello("like"));


        System.out.println(hello.hello("keke"));

        System.out.println(hello.hello("xiaowang"));
    }

    /**
     * 代理服务，调用目标方法
     *
     * @param serviceClass 服务类
     * @return {@link T}
     */
    public static <T> T getProxyService(Class<T> serviceClass) throws ExecutionException, InterruptedException {
        Object o = Proxy.newProxyInstance(
                serviceClass.getClassLoader(),   // 类加载器
                new Class[]{serviceClass},      // 实例化的接口
                getInvocationHandler(serviceClass));
        return (T) o;
    }

    /**
     * invocation method
     *
     * @param serviceClass 服务类
     * @return {@link InvocationHandler}
     */
    private static <T> InvocationHandler getInvocationHandler(Class<T> serviceClass) {
        return (proxy, method, args) -> {
            // 1.将方法调用转换为消息对象
            int sqeId = SqeIdGetter.getId();
            RpcRequestMessage resp = getRpcRequestMessage(serviceClass, method, args, sqeId);
            // 2.发送消息
            getChannel().writeAndFlush(resp);

            // 3.准备一个promise对象,接收结果                promise 运行的线程
            DefaultPromise<Object> promise = new DefaultPromise<>(getChannel().eventLoop());
            RpcResponseMessageHandler.RPC_PROMISES.put(sqeId, promise);
            AtomicReference<Object> now = new AtomicReference<>();
                   /* promise.addListener(future -> {
                        future.sync();
                        if (future.isSuccess()) {
                            now.set(future.getNow());

                        } // 异常
                        else
                            throw new RuntimeException(future.cause());
                    });*/
            promise.sync();
            if (promise.isSuccess()) {
                now.set(promise.getNow());
            } // 异常
            else
                throw new RuntimeException(promise.cause());
            // 4. 同步返回结果
            return now.get();
        };
    }

    /**
     * 封装成 rpc请求消息
     *
     * @param serviceClass 服务类
     * @param method       方法
     * @param args         arg游戏
     * @param sqeId        sqe id
     * @return {@link RpcRequestMessage}
     */
    private static <T> RpcRequestMessage getRpcRequestMessage(Class<T> serviceClass, java.lang.reflect.Method method, Object[] args, int sqeId) {
        Class<?>[] types = method.getParameterTypes();
        String[] parameterTypes = new String[types.length];
        for (int i = 0; i < types.length; i++) {
            parameterTypes[i] = types[i].getName();
        }

        return new RpcRequestMessage(
                sqeId,
                serviceClass.getName(),
                method.getName(),
                method.getReturnType().getName(),
                parameterTypes,
                args
        );
    }

    /** 通道 */
    public static volatile Channel channel;
    private static final Object LOCK = new Object();

    /**
     * get channel
     *
     * @return {@link Channel}
     */

    public static Channel getChannel() {
        if (channel != null) {
            return channel;
        }
        synchronized (LOCK) { //  t2
            if (channel != null) { // t1
                return channel;
            }
            initChannel();
            return channel;
        }
    }

    /**
     * 初始化通道
     */
    private static void initChannel() {
        NioEventLoopGroup group = new NioEventLoopGroup();
        AtomicReference<String> username = new AtomicReference<>("null");

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.group(group);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                // ch.pipeline().addLast(getLogHandler());  // log
                ch.pipeline().addLast(getLikeProtocolCodecSharable());  // codec
                ch.pipeline().addLast(getLikeProtocolFrameDecoder());  // LengthFieldBasedFrameDecoder

                ch.pipeline().addLast(new RpcResponseMessageHandler());
            }
        });
        try {
            channel = bootstrap.connect(RpcServer.serverHost, RpcServer.serverPort).sync().channel();
            // 异步关闭
            channel.closeFuture().addListener(close -> {
                group.shutdownGracefully();
            });
        } catch (Exception e) {
            log.error("client error", e);
        }
    }
}
