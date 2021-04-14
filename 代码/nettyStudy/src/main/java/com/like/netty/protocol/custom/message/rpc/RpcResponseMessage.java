package com.like.netty.protocol.custom.message.rpc;

import com.like.netty.protocol.custom.message.AbstractResponseMessage;
import lombok.Data;

/**
 * Create By like On 2021-04-14 15:23
 * <p>
 * rpc 响应消息
 */
@Data
public class RpcResponseMessage extends AbstractResponseMessage {

    /** 正常 返回值 */
    private Object returnValue;

    /** 异常值 */
    private Exception exceptionValue;

    @Override
    public int getMessageType() {
        return RpcResponseMessage;
    }
}
