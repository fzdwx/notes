package com.like.netty.protocol.custom.message.chat;

import com.like.netty.protocol.custom.message.Message;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class GroupMembersResponseMessage extends Message {

    private Set<String> members;

    public GroupMembersResponseMessage(Set<String> members) {
        this.members = members;
    }

    @Override
    public int getMessageType() {
        return GroupMembersResponseMessage;
    }
}
