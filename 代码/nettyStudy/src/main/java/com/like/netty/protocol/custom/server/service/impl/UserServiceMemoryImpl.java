package com.like.netty.protocol.custom.server.service.impl;

import com.like.netty.protocol.custom.server.service.UserService;
import org.springframework.util.StringUtils;

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
    public boolean login(String username, String password) {
        final String pwd = users.get(username);
        if (StringUtils.isEmpty(pwd)) return false;
        return pwd.equals(password);
    }
}
