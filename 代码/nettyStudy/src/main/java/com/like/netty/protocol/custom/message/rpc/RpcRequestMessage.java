package com.like.netty.protocol.custom.message.rpc;

import com.like.netty.protocol.custom.message.Message;
import lombok.Data;

/**
 * Create By like On 2021-04-14 15:24
 */
@Data
public class RpcRequestMessage extends Message {

    public RpcRequestMessage(int sequenceId, String interfaceName, String methodName, Class<?> returnType, Class[] parameterTypes, Object[] parameterValue) {
        this.setSequenceId(sequenceId);

        this.interfaceName = interfaceName;
        this.methodName = methodName;
        this.returnType = returnType;
        this.parameterTypes = parameterTypes;
        this.parameterValue = parameterValue;
    }

    /** 调用的接口名称 */
    private String interfaceName;
    /** 调用的方法名称 */
    private String methodName;
    /** 方法返回类型 */
    private Class<?> returnType;
    /** 方法参数类型 */
    private Class[] parameterTypes;
    /** 方法参数值 */
    private Object[] parameterValue;


    @Override
    public int getMessageType() {
        return RpcRequestMessage;
    }
}
