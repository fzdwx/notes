package com.like.netty.protocol.custom.message;

/**
 * Create By like On 2021-04-12 21:57
 */
public class RegisterResponseMessage extends AbstractResponseMessage {
    public RegisterResponseMessage(boolean success, String reason) {
        super(success, reason);
    }

    @Override
    public int getMessageType() {
        return RegisterResponseMessage;
    }
}
