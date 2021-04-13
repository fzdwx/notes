package com.like.netty.protocol.custom.message.chat;

import com.like.netty.protocol.custom.message.AbstractResponseMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class GroupChatResponseMessage extends AbstractResponseMessage {
    private String from;
    private String content;

    public GroupChatResponseMessage(boolean success, String reason) {
        super(success, reason);
    }

    public GroupChatResponseMessage(String from, String content) {
        this.from = from;
        this.content = content;
    }
    @Override
    public int getMessageType() {
        return GroupChatResponseMessage;
    }
}
