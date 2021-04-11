package com.like.netty.protocol.custom.chat.message;

public class PongMessage extends Message {
    @Override
    public int getMessageType() {
        return PongMessage;
    }
}
