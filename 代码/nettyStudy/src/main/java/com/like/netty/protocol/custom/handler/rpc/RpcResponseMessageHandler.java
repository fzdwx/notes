package com.like.netty.protocol.custom.handler.rpc;

import com.like.netty.protocol.custom.message.rpc.RpcResponseMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.Promise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Create By like On 2021-04-14 15:23
 * <p>
 * rpc 响应消息处理
 */
public class RpcResponseMessageHandler extends SimpleChannelInboundHandler<RpcResponseMessage> {
    private final static Logger log = LoggerFactory.getLogger(RpcResponseMessageHandler.class);

    /** 序号：接收结果的promise */
    public static final Map<Integer, Promise<Object>> RPC_PROMISES = new ConcurrentHashMap<>();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponseMessage msg) throws Exception {
        log.info("#channelRead0(..):rpc 调用{} ",msg.getReturnValue());

        // 返回promise
        Promise<Object> promise = RPC_PROMISES.get(msg.getSequenceId());
        if (promise != null) {
            if (Objects.isNull(msg.getExMessage())) {
                promise.setSuccess(msg.getReturnValue());
            } else {
                promise.setFailure(msg.getExMessage());
            }
        }
    }
}
