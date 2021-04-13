package com.like.netty.protocol.custom.server.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.like.netty.protocol.custom.message.chat.LoginResponseMessage;
import com.like.netty.protocol.custom.server.service.UserService;
import com.like.netty.protocol.custom.server.session.SessionFactory;
import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用户 service 内存 实现
 * Create By like On 2021-04-11 15:03
 */
public class UserServiceMemoryImpl implements UserService {

    private Map<String, String> users = new ConcurrentHashMap<String, String>() {
        {put("like", "like");}

        {put("root", "root");}

        {put("lucy", "lucy");}

        {put("jack", "jack");}

        {put("bob", "bob");}

        {put("brown", "brown");}
    };

    @Override
    public LoginStats login(String username, String password) {
        final String pwd = users.get(username);
        if (StrUtil.isBlank(pwd)) return new LoginStats(LoginStats.noUserInformation, false);
        boolean equalsPwd = password.equals(pwd);
        if (equalsPwd) {
            final Channel channel = SessionFactory.getSession().getChannel(username);
            if (ObjectUtil.isNotNull(channel)) {
                channel.writeAndFlush(new LoginResponseMessage(false, "账户异地登录，您已被挤下线"));
                channel.close();
            } // END 判断用户是否重复登录
            return new LoginStats(LoginStats.success, true);
        }
        return new LoginStats(LoginStats.passwordMistake, false);


    }

    @Override
    public boolean register(String username, String password) {
        final String pwd = users.get(username);
        // 用户已经注册过了
        if (StrUtil.isNotBlank(pwd)) { return false;}

        users.putIfAbsent(username, password);
        return true;
    }
}
