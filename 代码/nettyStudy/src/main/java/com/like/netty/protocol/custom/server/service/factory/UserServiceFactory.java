package com.like.netty.protocol.custom.server.service.factory;

import com.like.netty.protocol.custom.server.service.UserService;
import com.like.netty.protocol.custom.server.service.impl.UserServiceMemoryImpl;

public abstract class UserServiceFactory {

    private static UserService userService = new UserServiceMemoryImpl();

    public static UserService getUserService() {
        return userService;
    }
}
