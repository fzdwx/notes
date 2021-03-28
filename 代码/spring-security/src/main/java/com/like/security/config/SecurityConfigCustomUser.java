package com.like.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-03-28 16:23
 */
//@Configuration
public class SecurityConfigCustomUser extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 1.密码加密器
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        // 2.设置登录用户信息
        String passwd = encoder.encode("root");
        String uname = "root";
        // 3.设置
        auth.inMemoryAuthentication()
            .withUser(uname)
            .password(passwd)
            .roles("admin");
    }

    /**
     * 在ioc中添加密码编码器
     * @return {@link PasswordEncoder}
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
