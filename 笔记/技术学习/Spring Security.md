# Spring Security

https://spring.io/projects/spring-security

主要功能

~~~
1.用户认证
2.权限控制
~~~





## 整合：

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

![image-20210328150547382](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210328150554.png)

![](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210328150600.png)





## 测试

![image-20210328150702704](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210328150702.png)

![image-20210328150710305](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210328150710.png)





## 基本原理

本质是一个过滤器链

重点看三个过滤器

### FilterSecurityInterceptor

是一个方法级的权限过滤器，基本位于过滤链的最底部



![image-20210328151506450](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210328151506.png)





### ExceptionTranslationFilter

异常过滤器，用来处理在认证权限授权过程中抛出的异常







### UsernamePasswordAuthenticationFilter

用户名密码校验，对/login POST的请求做拦截

![image-20210328151733335](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210328151733.png)







## 两个重要接口

UserDetailsService：查询数据库用户名和密码的过程

~~~
继承 UsernamePasswordAuthenticationFilter 重写方法
实现 UserDetailsService 编写查询数据过程 返回 User 
~~~



PasswordEncoder

~~~
数据加密接口，用户返回User对象里面密码加密
~~~







## 使用数据查询用户登录

注册自己的userDetailsService的实现类

```java
@Configuration
public class SecurityConfigWithUserDetailsService extends WebSecurityConfigurerAdapter {

    @Qualifier("userDetailsServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
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
```



实现自己的逻辑：查询数据库

~~~java
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1.调用userDao 根据用户名查询
        Users users = userDao.selectOne(new QueryWrapper<Users>().eq("username", username));

        // 2.判断
        if (users == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        // 3.通过校验
        List<GrantedAuthority> roles = AuthorityUtils.commaSeparatedStringToAuthorityList("role");
        return new User(users.getUsername(), new BCryptPasswordEncoder().encode(users.getPassword()), roles);
    }
}
~~~





# 自定义登录页面

```java
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
        .antMatchers("/", "/noauth").permitAll().anyRequest().authenticated()  // 访问这些路径不需要认证
        .and().csrf().disable();             // 关闭csrf 防护
}
```





# 基于角色或权限进行访问控制

## 	1.hasAuthority

如果当前主体具有指定权限，则返回true

做不到多个

![image-20210329200930688](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210329200930.png)

![image-20210329201221275](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210329201221.png)



## 2.hasAnyAuthority

可以用多个权限访问

![image-20210329201651693](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210329201651.png)





## 3.hasRole

![image-20210329202419863](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210329202420.png)

![image-20210329202427981](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210329202428.png)



## 4.hasAnyRoles

同上