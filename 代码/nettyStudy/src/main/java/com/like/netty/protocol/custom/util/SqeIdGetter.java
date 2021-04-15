package com.like.netty.protocol.custom.util;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * sqe id getter
 */
public abstract class SqeIdGetter {
    public static final AtomicInteger id = new AtomicInteger();

    public static final int getId() {
        return id.incrementAndGet();
    }
}