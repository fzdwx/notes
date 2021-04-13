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
    LoginStats login(String username, String password);

    /**
     * 注册
     *
     * @param username 用户名
     * @param password 密码
     * @return boolean
     */
    boolean register(String username, String password);

    class LoginStats {

        public String getType() {
            return type;
        }

        public boolean getStatus() {
            return status;
        }

        /** 类型 */
        private String type;
        /** 状态 */
        private boolean status;

        public LoginStats() {

        }

        public LoginStats(String type, boolean status) {
            this.type = type;
            this.status = status;
        }

        /** 成功 */
        public static final String success = "登录成功";
        /** 密码错误 */
        public static final String passwordMistake = "用户名或密码不正确";
        /** 无用户信息 需要注册 */
        public static final String noUserInformation = "账户未注册";
    }
}
