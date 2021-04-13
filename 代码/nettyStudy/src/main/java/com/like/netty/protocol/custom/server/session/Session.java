package com.like.netty.protocol.custom.server.session;

import io.netty.channel.Channel;

/**
 * Create By like On 2021-04-11 15:10
 * session 顶级接口
 */
public interface Session {

    /**
     * 用户绑定到channel上
     *
     * @param channel  被绑定的channel
     *                 {@link Channel}
     * @param username 绑定的用户
     *                 {@link String}
     */
    void bind(Channel channel, String username);


    /**
     * 解开绑定
     *
     * @param channel 哪个channel 要解开绑定
     *                {@link Channel}
     * @return
     */
    String unbind(Channel channel);


    /**
     * 获取channel
     *
     * @param username 用户名
     *                 {@link String}
     * @return {@link Channel}
     */
    Channel getChannel(String username);

    /**
     * 获取属性
     *
     * @param channel 通道
     *                {@link Channel}
     * @param key     关键
     *                {@link String}
     * @return {@link Object}
     */
    Object getAttr(Channel channel, String key);

    /**
     * 设置属性
     *
     * @param channel 通道
     *                {@link Channel}
     * @param key     key
     *                {@link String}
     * @param val     值
     *                {@link Object}
     */
    void setAttr(Channel channel, String key, Object val);

}
