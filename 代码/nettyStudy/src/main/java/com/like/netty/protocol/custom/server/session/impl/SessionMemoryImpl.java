package com.like.netty.protocol.custom.server.session.impl;

import com.like.netty.protocol.custom.server.session.Session;

import io.netty.channel.Channel;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Create By like On 2021-04-11 15:15
 * 基于内存实现的 session
 *
 * @see Session
 */
public class SessionMemoryImpl implements Session {

    /** user : channel */
    private final Map<String, Channel> userMapChannel = new ConcurrentHashMap<>();
    /** channel : user */
    private final Map<Channel, String> channelMapUser = new ConcurrentHashMap<>();
    /** channel : { key : val} */
    private final Map<Channel, Map<String, Object>> channelAttrMap = new ConcurrentHashMap<>();


    @Override
    public void bind(Channel channel, String username) {
        userMapChannel.put(username, channel);
        channelMapUser.put(channel, username);
        channelAttrMap.put(channel, new ConcurrentHashMap<>());
    }

    @Override
    public void unbind(Channel channel) {
        final String user = channelMapUser.remove(channel);
        userMapChannel.remove(user);
        channelAttrMap.remove(channel);
    }

    @Override
    public Channel getChannel(String username) {
        return userMapChannel.get(username);
    }

    @Override
    public Object getAttr(Channel channel, String key) {
        return channelAttrMap.get(channel).get(key);
    }

    @Override
    public void setAttr(Channel channel, String key, Object val) {
        channelAttrMap.get(channel).put(key, val);
    }
}
