package com.like.netty.protocol.custom.server.session;

import lombok.Data;

import java.util.Collections;
import java.util.Set;

@Data
/**
 * 聊天组，即聊天室
 */
public class Group {
    /** 聊天室名称 */
    private String name;
    /** 创造者 */
    private String creator;
    /** 聊天室成员 */
    private Set<String> members;

    /** 空 */
    private static final String empty = "empty";
    /** 空的组 */
    public static final Group EMPTY_GROUP = new Group(empty, Collections.emptySet(), empty);

    public Group(String name, Set<String> members, String creator) {
        this.name = name;
        this.members = members;
        this.creator = creator;
    }
}
