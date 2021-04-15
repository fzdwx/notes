package com.like.netty.protocol.custom.handler.rpc;

import cn.hutool.core.util.ClassUtil;
import com.like.netty.protocol.custom.message.rpc.RpcRequestMessage;
import com.like.netty.protocol.custom.message.rpc.RpcResponseMessage;
import com.like.netty.protocol.custom.server.service.factory.ServicesFactory;
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

        resp.setSequenceId(msg.getSequenceId());
        try {  // 反射调用目标方法 invoke
            Object impl = ServicesFactory.getService(Class.forName(msg.getInterfaceName()));
            final Method method = impl.getClass().getMethod(msg.getMethodName(), ClassUtil.getClasses(msg.getParameterTypes()));
            final Object res = method.invoke(impl, msg.getParameterValue()); // invoke

            resp.setReturnValue(res);
        } catch (Exception e) {  // 异常
            e.printStackTrace();
            resp.setExMessage(new Exception("远程调用出错: "+e.getCause().getMessage()));
        }

        ctx.writeAndFlush(resp);
    }
}
