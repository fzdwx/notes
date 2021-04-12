package com.like.netty.protocol.custom.message;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class ChatRequestMessage extends Message {
    /** 内容 */
    private String content;
    /** 消息接受者 username */
    private String to;
    /** 消息发送者 username */
    private String from;

    public ChatRequestMessage() {
    }

    /**
     * 聊天请求消息
     *
     * @param from    从
     * @param to      来
     * @param content 内容
     */
    public ChatRequestMessage(String from, String to, String content) {
        this.from = from;
        this.to = to;
        this.content = content;
    }

    @Override
    public int getMessageType() {
        return ChatRequestMessage;
    }
}
