# 	Spring Security

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





# 定义没有权限访问的资源 跳转到统一的页面

![image-20210329204213801](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210329204213.png)

![image-20210329204228307](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210329204228.png)

![image-20210329204238247](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210329204238.png)







# 注解

```
@EnableGlobalMethodSecurity(securedEnabled = true)  开启
```

~~~
@Secured
@PreAuthorize
@PostAuthorize
@
~~~





## @Secured

作用：用户有某权限，才可以访问

没有配置权限：

```java
@Secured({
    "ROLE_SALE","ROLE_MANAGER"
})
@GetMapping("update")
public String update(){
    return "hello update";
}
```

![image-20210404131624326](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210404131631.png)

配置权限后

![image-20210404131721865](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210404131721.png)

![image-20210404131955494](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210404131955.png)



## @PreAuthorize

使用前，开启注解

![image-20210404133153747](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210404133153.png)

进入方法前的权限验证

![image-20210404133046140](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210404133046.png)



## @PostAuthorize

方法执行之后，检验权限





## @PostFilter 

方法返回数据过滤

![image-20210404133941204](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210404133941.png)





## @PreFilter

方法传入数据





# 用户注销

## 配置

添加登出跳转信息

![image-20210404134456990](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210404134457.png)



```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>登录成功</title>

</head>
<body>
<H1>登录成功</H1>
<a href="/logout">登出</a>
</body>
</html>
```





## 测试

![image-20210404134633469](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210404134633.png)





# 基于数据库实现“记住我”（自动登录）

![image-20210404135306662](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210404135306.png)



RememberMeServices 



```
PersistentTokenBasedRememberMeServices

JdbcTokenRepositoryImpl  有建表语句
```



![image-20210404140117854](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210404140117.png)



## 实现步骤

![image-20210404141109136](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210404141109.png)

![image-20210404141120470](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210404141120.png)



![image-20210404141543627](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210404141543.png)

![image-20210404141538320](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210404141538.png)







# CSRF 

跨域请求伪造，默认对除GET请求外进行保护









# 分布式授权

统一认证授权

应用接入认证



## oauth 2.0

是一个开放标准，允许用户授权第三方应用访问他们存储在另外的服务提供者上的信息，而不需要将用户名和密码提供给第三方应用。

![image-20210404144353899](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210404144354.png)



## Spring Cloud Security OAuth 2

- AuthorizationEndpoint 服务用于认证请求 /oauth/autorize
- TokenEndpoint 服务用于访问令牌的请求  /oauth/token
- OAuth2AuthenticationProcessingFilter 用来对请求给出的身份令牌解析鉴权

UAA授权服务 Order订单资源服务

认证请求流程：

1. 客户端请求UAA授权符文进行认证
2. 认证通过后UAA颁发令牌
3. 客户端携带token请求资源服务
4. 资源服务验证令牌合法性，合法返回资源