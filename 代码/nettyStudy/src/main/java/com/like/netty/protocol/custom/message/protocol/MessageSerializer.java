package com.like.netty.protocol.custom.message.protocol;

import java.io.*;

/**
 * Create By like On 2021-04-13 16:39
 * <p>
 * 协议序列化 接口
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


    enum Algorithm implements MessageSerializer {

        JDK() {
            public static final int algorithmType = 0;

            @Override
            public <T> byte[] serialization(T object) {
                try {
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(bos);
                    oos.writeObject(object);
                    return bos.toByteArray();
                } catch (IOException e) {
                    throw new RuntimeException("序列化失败:{}", e);
                }
            }

            @Override
            public <T> T deserialization(Class<T> clazz, byte[] bytes) {
                try {
                    ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
                    return (T) ois.readObject();
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException("反序列化失败:{}", e);
                }
            }

            @Override
            public int algorithmType() {
                return algorithmType;
            }
        };


    }
}
