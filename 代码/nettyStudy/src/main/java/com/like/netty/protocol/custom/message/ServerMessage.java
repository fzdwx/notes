package com.like.netty.protocol.custom.message;

import lombok.Data;

/**
 * Create By like On 2021-04-13 14:30
 * <p>
 * 服务器消息
 */
@Data
public class ServerMessage extends Message {

    private String serverMessage;

    public ServerMessage(String serverMessage) {
        this.serverMessage = serverMessage;
    }

    @Override
    public int getMessageType() {
        return ServerMessage;
    }
}
