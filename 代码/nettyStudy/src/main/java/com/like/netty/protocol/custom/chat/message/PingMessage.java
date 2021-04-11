package com.like.netty.protocol.custom.chat.message;

public class PingMessage extends Message {
    @Override
    public int getMessageType() {
        return PingMessage;
    }
}
