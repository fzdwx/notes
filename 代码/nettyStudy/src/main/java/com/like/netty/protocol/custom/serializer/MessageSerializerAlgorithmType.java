package com.like.netty.protocol.custom.serializer;

/**
 * 序列化算法类型
 *
 * @see MessageSerializer
 */
public enum MessageSerializerAlgorithmType {

    JSON(1, JSONMessageSerializerImpl.class),
    JDK(0, JDKMessageSerializerImpl.class);

    public Class<?> clazz;
    public int code;

    MessageSerializerAlgorithmType(int code, Class<?> clazz) {
        this.clazz = clazz;
        this.code = code;
    }
}