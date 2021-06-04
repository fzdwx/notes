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
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.ArrayList;
import java.util.List;

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
    @Autowired
    private TokenStore tokenStore;
    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;
    @Autowired
    private JwtTokenStoreConfig.JwtTokenEnhancer jwtTokenEnhancer;

    /**
     * 配置使用 AuthenticationManager 实现用户认证的功能 对应在 {@link SecurityConfig#authenticationManagerBean()}
     * 配置使用 userDetailsService 获取用户信息 对应在 {@link SecurityConfig#userDetailsService()}
     * 配置使用 jwt 存储token
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> delegates = new ArrayList<>();
        delegates.add(jwtTokenEnhancer); //配置JWT的内容增强器
        delegates.add(jwtAccessTokenConverter);
        enhancerChain.setTokenEnhancers(delegates);
        endpoints.authenticationManager(authenticationManager)
                 .userDetailsService(userDetailsService)
                 .tokenStore(tokenStore) //配置令牌存储策略
                 .accessTokenConverter(jwtAccessTokenConverter)
                 .tokenEnhancer(enhancerChain);
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
        // @formatter:off
        // ~ 为 client_id 和 client_secret 开启表单验证, 会启用一个名为 ClientCredentialsTokenEndpointFilter 的过滤器.
        //   并会把这个过滤器放在 BasicAuthenticationFilter 之前,
        //   这样如果在 ClientCredentialsTokenEndpointFilter 完成了校验 (SecurityContextHolder.getContext().getAuthentication()),
        //   且请求头中即使有 Authorization: basic xxx, 也会被 BasicAuthenticationFilter 忽略.
        //   ref: AuthorizationServerSecurityConfigurer#clientCredentialsTokenEndpointFilter, BasicAuthenticationFilter#doFilterInternal
        // ~ 如果不配置这一行, 默认就会通过 BasicAuthenticationFilter.
        // security.allowFormAuthenticationForClients();

     /*   security
                // ~ ExceptionTranslationFilter handling
                //   在 Client Credentials Grant 和 Resource Owner Password Grant 模式下, 客户端凭证有误时会触发 authenticationEntryPoint
                // -----------------------------------------------------------------------------------------------------

                // ~ AuthenticationEntryPoint: called by ExceptionTranslationFilter when AuthenticationException be thrown.
                .authenticationEntryPoint(authenticationEntryPoint)
                // ~ AccessDeniedHandler: called by ExceptionTranslationFilter when AccessDeniedException be thrown.
                .accessDeniedHandler(accessDeniedHandler)
                // ~ 为 /oauth/token 端点 (TokenEndpoint) 添加自定义的过滤器
                .addTokenEndpointAuthenticationFilter(new CustomClientCredentialsTokenEndpointFilter(passwordEncoder, userDetailsService, authenticationEntryPoint))
        ;
        // @formatter:on*/
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
               .authorizedGrantTypes("authorization_code", "password","refresh_token");//配置grant_type，表示授权类型
    }
}