package com.like.netty.protocol.custom.server.service.impl;

import com.like.netty.protocol.custom.server.service.HelloService;

/**
 * Create By like On 2021-04-14 15:51
 */
public class HelloServiceImpl implements HelloService {

    @Override
    public String hello(String name) {
        return name + " hello !";
    }
}
