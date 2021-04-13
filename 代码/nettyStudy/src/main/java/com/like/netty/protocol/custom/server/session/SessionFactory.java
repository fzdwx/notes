package com.like.netty.protocol.custom.server.session;

import com.like.netty.protocol.custom.server.session.impl.SessionMemoryImpl;

public abstract class SessionFactory {

    private static Session session = new SessionMemoryImpl();

    public static Session getSession() {
        return session;
    }
}
