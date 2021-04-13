package com.like.netty.protocol.custom.serializer;

/**
 * Create By like On 2021-04-13 16:39
 * <p>
 * 协议序列化 接口
 * <p>
 *
 * @Description: 如果需要自定义序列
 *         1. 实现 MessageSerializer 接口
 *         2. 在 MessageSerializerAlgorithmType 中添加对应对象
 *         3. 在/resources/application.setting 中修改为对应的 MessageSerializerAlgorithmType 的名字
 * @see JSONMessageSerializerImpl default
 * @see JDKMessageSerializerImpl
 */
public interface MessageSerializer {

    /**
     * 序列化
     *
     * @param object 对象
     * @return {@link T}
     */
    <T> byte[] serialization(T object);

    /**
     * 反序列化
     *
     * @param clazz clazz
     * @param bytes 字节
     * @return {@link T}
     */
    <T> T deserialization(Class<T> clazz, byte[] bytes);

    /**
     * 序列化算法类型
     * 0 - jdk
     * 1 - json
     *
     * @return int
     */
    int algorithmType();
}
