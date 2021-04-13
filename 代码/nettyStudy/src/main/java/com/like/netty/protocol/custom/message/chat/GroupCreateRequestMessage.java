package com.like.netty.protocol.custom.message.chat;

import com.like.netty.protocol.custom.message.Message;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class GroupCreateRequestMessage extends Message {
    private String groupName;
    private String creator;
    private Set<String> members;

    public GroupCreateRequestMessage(String groupName, Set<String> members, String creator) {
        this.groupName = groupName;
        this.members = members;
        this.creator = creator;
    }

    @Override
    public int getMessageType() {
        return GroupCreateRequestMessage;
    }
}
