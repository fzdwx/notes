package com.like.netty.eventloop;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Create By like On 2021-04-10 12:07
 * 在eventLoopGroup中提交任务
 */
public class EventLoopTest {
    private final static Logger log = LoggerFactory.getLogger(EventLoopTest.class);

    public static void main(String[] args) {
        testThreadCount();
    }

    @Test
    static void testThreadCount() {
        EventLoopGroup boss = new NioEventLoopGroup(4);

        // 1.遍历eventLoop
        /* for (EventExecutor eventExecutor : boss) {
            System.out.println(eventExecutor);
        }*/

        // 2.提交一个普通任务
        boss.next().submit(()->{
            log.info(EventLoopTest.class.getName() + "#testThreadCount(..): 普通任务");
        });

        // 3.提交一个定时任务
        boss.next().scheduleAtFixedRate(()->{
            log.info(EventLoopTest.class.getName() + "#testThreadCount(..): 定时任务");
            // 延迟3秒运行  每隔1秒执行一次
        }, 3, 1, TimeUnit.SECONDS);
    }
}
