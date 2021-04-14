package com.like.netty.protocol.custom.handler.rpc;

import com.like.netty.protocol.custom.message.rpc.RpcRequestMessage;
import com.like.netty.protocol.custom.message.rpc.RpcResponseMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.lang.reflect.Method;

/**
 * Create By like On 2021-04-14 15:18
 * <p>
 * rpc 请求消息处理
 */
public class RpcRequestMessageHandler extends SimpleChannelInboundHandler<RpcRequestMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequestMessage msg) {

        final RpcResponseMessage resp = new RpcResponseMessage();
        try {  // 反射调用目标方法 invoke
            final Class<?> clazz = Class.forName(msg.getInterfaceName());
            final Object impl = clazz.newInstance();

            Class<?>[] parameterType = new Class[msg.getParameterTypes().length];
            for (int i = 0; i < msg.getParameterTypes().length; i++) {
                parameterType[i] = Class.forName(msg.getParameterTypes()[i]);
            }
            final Method method = impl.getClass().getMethod(msg.getMethodName(), parameterType);
            final Object res = method.invoke(impl, msg.getParameterValue());

            resp.setReturnValue(res);
        } catch (Exception e) {  // 异常
            e.printStackTrace();
            resp.setExMessage(e.getMessage());
        }

        ctx.writeAndFlush(resp);
    }
}
