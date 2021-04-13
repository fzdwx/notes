package com.like.netty.protocol.custom.serializer;

import java.io.*;

/**
 * Create By like On 2021-04-13 20:34
 * <p>
 * jdk 序列化
 */
public class JDKMessageSerializerImpl implements MessageSerializer {
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
        return MessageSerializerAlgorithmType.JDK.code;
    }
}
