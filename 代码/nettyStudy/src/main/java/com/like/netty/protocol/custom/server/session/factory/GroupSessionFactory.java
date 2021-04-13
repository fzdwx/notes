package com.like.netty.protocol.custom.server.session.factory;

import com.like.netty.protocol.custom.server.session.GroupSession;
import com.like.netty.protocol.custom.server.session.impl.GroupSessionMemoryImpl;

public abstract class GroupSessionFactory {

    private static GroupSession session = new GroupSessionMemoryImpl();

    public static GroupSession getGroupSession() {
        return session;
    }
}
