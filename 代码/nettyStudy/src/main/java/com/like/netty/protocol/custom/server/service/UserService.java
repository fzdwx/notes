package com.like.netty.protocol.custom.server.service;

/**
 * Create By like On 2021-04-11 15:02
 * 用户 service
 */
public interface UserService {

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @return boolean
     */
    boolean login(String username, String password);
}
