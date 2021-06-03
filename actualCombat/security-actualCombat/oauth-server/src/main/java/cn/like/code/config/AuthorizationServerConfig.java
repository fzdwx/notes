package cn.like.code.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

/**
 * 授权服务器配置
 * <pre>
 *     授权服务 都是使用 client-id + client-secret 进行的客户端认证，不要和用户认证混淆。
 * </pre>
 * @author like
 * @date 2021/06/02
 */
@Configuration
@EnableAuthorizationServer // 声明开启 OAuth 授权服务器的功能
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 配置使用 AuthenticationManager 实现用户认证的功能 对应在 {@link SecurityConfig#authenticationManagerBean()}
     * 配置使用 userDetailsService 获取用户信息 对应在 {@link SecurityConfig#userDetailsService()}
     *
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.authenticationManager(authenticationManager)
                 .userDetailsService(userDetailsService);
    }

    /**
     * 设置 /oauth/check_token 端点 所有人都可以访问
     *
     * @param security 安全
     * @throws Exception 异常
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients();
        security.checkTokenAccess("permitAll()"); //
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
               .withClient("myClient")//配置client_id
               .secret(passwordEncoder.encode("123456"))//配置client_secret
               .accessTokenValiditySeconds(3600)//配置访问token的有效期
               .refreshTokenValiditySeconds(864000)//配置刷新token的有效期
               .redirectUris("http://127.0.0.1:9090/callback")//配置redirect_uri，用于授权成功后跳转
               .scopes("all","read_userinfo")//配置申请的权限范围
               .authorizedGrantTypes("authorization_code", "password");//配置grant_type，表示授权类型
    }
}