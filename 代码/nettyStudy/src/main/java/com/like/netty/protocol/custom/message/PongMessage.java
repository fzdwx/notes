package com.like.netty.protocol.custom.message;

/**
 * Create By like On 2021-04-13 14:53
 * 心跳数据包
 */
public class PongMessage extends Message {
    private String info;

    public PongMessage(String info) {
        this.info = info;
    }

    @Override
    public int getMessageType() {
        return PongMessage;
    }
}
