package com.like.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-03-28 16:48
 */
@Configuration
public class SecurityConfigWithUserDetailsService extends WebSecurityConfigurerAdapter {

    @Resource
    private UserDetailsService userDetailsService;

    /**
     * 配置 使用自定义的service
     * @param auth 身份验证
     * @throws Exception 异常
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    /**
     * 配置 登录页面 以及 允许哪些请求可以不用通过登录就可以访问
     * @param http http
     * @throws Exception 异常
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
            .loginPage("/login.html")                              // 自定义登录的页面
            .loginProcessingUrl("/user/login")                    // 登录信息提交到哪个controller 具体逻辑不用管
            .defaultSuccessUrl("/hello").permitAll()             // 登录成功只有，跳转路径
            .and().authorizeRequests()
            .antMatchers("/", "/noauth").permitAll() // 访问这些路径不需要认证
            .antMatchers("/adminOnly").hasAuthority("admin")
            .antMatchers("/adminAndRole").hasAnyAuthority("admin,role")  // 使用多个权限
            .antMatchers("/producer").hasRole("producer")  // 是producer 这个角色才可以访问
            .anyRequest().authenticated()
            .and().csrf().disable();             // 关闭csrf 防护

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
