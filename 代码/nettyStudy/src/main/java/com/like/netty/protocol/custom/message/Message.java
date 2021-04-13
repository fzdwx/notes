package com.like.netty.protocol.custom.message;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
public abstract class Message implements Serializable {
    @JsonIgnore
    public static Class<?> getMessageClass(int messageType) {
        return messageClasses.get(messageType);
    }

    private int sequenceId;

    private int messageType;

    public abstract int getMessageType();
    @JsonIgnore
    public static final int RegisterRequestMessage = -1;
    @JsonIgnore
    public static final int RegisterResponseMessage = -2;
    @JsonIgnore
    public static final int LoginRequestMessage = 0;
    @JsonIgnore
    public static final int LoginResponseMessage = 1;
    @JsonIgnore
    public static final int ChatRequestMessage = 2;
    @JsonIgnore
    public static final int ChatResponseMessage = 3;
    @JsonIgnore
    public static final int GroupCreateRequestMessage = 4;
    @JsonIgnore
    public static final int GroupCreateResponseMessage = 5;
    @JsonIgnore
    public static final int GroupJoinRequestMessage = 6;
    @JsonIgnore
    public static final int GroupJoinResponseMessage = 7;
    @JsonIgnore
    public static final int GroupQuitRequestMessage = 8;
    @JsonIgnore
    public static final int GroupQuitResponseMessage = 9;
    @JsonIgnore
    public static final int GroupChatRequestMessage = 10;
    @JsonIgnore
    public static final int GroupChatResponseMessage = 11;
    @JsonIgnore
    public static final int GroupMembersRequestMessage = 12;
    @JsonIgnore
    public static final int GroupMembersResponseMessage = 13;
    @JsonIgnore
    private static final Map<Integer, Class<?>> messageClasses = new HashMap<>();

    static {
        messageClasses.put(RegisterRequestMessage, RegisterRequestMessage.class);
        messageClasses.put(RegisterResponseMessage, RegisterResponseMessage.class);
        messageClasses.put(LoginRequestMessage, LoginRequestMessage.class);
        messageClasses.put(LoginResponseMessage, LoginResponseMessage.class);
        messageClasses.put(ChatRequestMessage, ChatRequestMessage.class);
        messageClasses.put(ChatResponseMessage, ChatResponseMessage.class);
        messageClasses.put(GroupCreateRequestMessage, GroupCreateRequestMessage.class);
        messageClasses.put(GroupCreateResponseMessage, GroupCreateResponseMessage.class);
        messageClasses.put(GroupJoinRequestMessage, GroupJoinRequestMessage.class);
        messageClasses.put(GroupJoinResponseMessage, GroupJoinResponseMessage.class);
        messageClasses.put(GroupQuitRequestMessage, GroupQuitRequestMessage.class);
        messageClasses.put(GroupQuitResponseMessage, GroupQuitResponseMessage.class);
        messageClasses.put(GroupChatRequestMessage, GroupChatRequestMessage.class);
        messageClasses.put(GroupChatResponseMessage, GroupChatResponseMessage.class);
        messageClasses.put(GroupMembersRequestMessage, GroupMembersRequestMessage.class);
        messageClasses.put(GroupMembersResponseMessage, GroupMembersResponseMessage.class);
    }

}
