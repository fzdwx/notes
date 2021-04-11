package com.like.netty.protocol.custom.message;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class GroupChatRequestMessage extends Message {
    private String content;
    private String groupName;
    private String from;

    public GroupChatRequestMessage(String from, String groupName, String content) {
        this.content = content;
        this.groupName = groupName;
        this.from = from;
    }

    @Override
    public int getMessageType() {
        return GroupChatRequestMessage;
    }
}
