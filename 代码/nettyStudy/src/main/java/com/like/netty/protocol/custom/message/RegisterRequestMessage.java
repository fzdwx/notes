package com.like.netty.protocol.custom.message;

/**
 * Create By like On 2021-04-12 21:29
 */
public class RegisterRequestMessage extends Message {

    /** 用户名 */
    private String username;
    /** 密码 */
    private String password;


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public RegisterRequestMessage(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public int getMessageType() {
        return RegisterRequestMessage;
    }
}
