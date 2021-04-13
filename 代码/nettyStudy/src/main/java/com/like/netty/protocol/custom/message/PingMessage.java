package com.like.netty.protocol.custom.message;

/**
 * Create By like On 2021-04-13 14:52
 * 心跳数据包
 */
public class PingMessage extends Message {


    private String info;

    public PingMessage(String info) {
        this.info = info;
    }

    @Override
    public int getMessageType() {
        return PingMessage;
    }
}
