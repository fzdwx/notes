package cn.like.code.config;

import cn.hutool.json.JSON;
import cn.like.code.config.support.CustomResourceServerTokenServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.OAuth2ClientProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

/**
 * 资源服务器配置
 *
 * @author pdd20
 * @date 2021/06/03
 */
@Configuration
@EnableResourceServer
@Slf4j
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Value("${spring.application.name}")
    private String RESOURCE_ID;
    @Value("${jwt.secret}")
    private String secret;
    /** 资源服务器保存的持有公钥的文件名 */
    private static final String AUTHORIZATION_SERVER_PUBLIC_KEY_FILENAME = "authorization-server.pub";
    /**
     * 授权服务器的 {@link org.springframework.security.oauth2.provider.endpoint.TokenKeyEndpoint} 供资源服务器请求授权服务器获取公钥的端点<br>
     * 在资源服务器中, 可以有两种方式获取授权服务器用于签名 JWT 的私钥对应的公钥:
     * <ol>
     *     <li>本地获取 (需要公钥文件)</li>
     *     <li>请求授权服务器提供的端点 (/oauth/token_key) 获取</li>
     * </ol>
     */
    private static final String AUTHORIZATION_SERVER_TOKEN_KEY_ENDPOINT_URL = "http://localhost:18957/token-customize-authorization-server/oauth/token_key";
    @Autowired
    private ResourceServerProperties resourceServerProperties;
    @Autowired
    private OAuth2ClientProperties oAuth2ClientProperties;
    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    /**
     * jwt访问令牌转换器
     *
     * @return {@link JwtAccessTokenConverter}
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        final JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        // jwtAccessTokenConverter.setVerifier(new org.springframework.security.jwt.crypto.sign.RsaVerifier(retrievePublicKey()));
        jwtAccessTokenConverter.setSigningKey(secret);
        return jwtAccessTokenConverter;
    }

    // /**
    //  * Description: 获取公钥 (Verifier Key)<br>
    //  * Details: 启动时调用
    //  *
    //  * @return java.lang.String
    //  * @author LiKe
    //  * @date 2020-07-22 11:45:40
    //  */
    // private String retrievePublicKey() {
    //     final ClassPathResource classPathResource = new ClassPathResource(AUTHORIZATION_SERVER_PUBLIC_KEY_FILENAME);
    //     try (
    //             // ~ 先从本地取读取名为 authorization-server.pub 的公钥文件, 获取公钥
    //             final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(classPathResource.getInputStream()))
    //     ) {
    //         log.debug("{} :: 从本地获取公钥 ...", RESOURCE_ID);
    //         return bufferedReader.lines().collect(Collectors.joining("\n"));
    //     } catch (IOException e) {
    //         // ~ 如果本地没有, 则尝试通过授权服务器的 /oauth/token_key 端点获取公钥
    //         log.debug("{} :: 从本地获取公钥失败: {}, 尝试从授权服务器 /oauth/token_key 端点获取 ...", RESOURCE_ID, e.getMessage());
    //         final RestTemplate restTemplate = new RestTemplate();
    //         final String responseValue = restTemplate.getForObject(AUTHORIZATION_SERVER_TOKEN_KEY_ENDPOINT_URL , String.class);
    //
    //         log.debug("{} :: 授权服务器返回原始公钥信息: {}", RESOURCE_ID, responseValue);
    //         return JSON.parseObject(JSON.parseObject(responseValue).getString("data")).getString("value");
    //     }
    // }


    /**
     * Description: 配置资源的访问规则. 默认情况下, 除了 /oauth/** 之外的所有资源都被保护<br>
     * Details: 默认情况下, {@link OAuth2WebSecurityExpressionHandler} 已经被注入, 形如 {@code http.authorizeRequests().expressionHandler(new OAuth2WebSecurityExpressionHandler())}
     *
     * <pre>
     * protected void configure(HttpSecurity http) throws Exception {
     *     http
     *         .authorizeRequests()
     *             .expressionHandler(new OAuth2WebSecurityExpressionHandler())
     *             .antMatchers("/photos").access("#oauth2.denyOAuthClient() and hasRole('ROLE_USER') or #oauth2.hasScope('read')")
     *             .antMatchers("/photos/trusted/**").access("#oauth2.denyOAuthClient() and hasRole('ROLE_USER') or #oauth2.hasScope('trust')")
     *             .antMatchers("/photos/user/**").access("#oauth2.denyOAuthClient() and hasRole('ROLE_USER') or #oauth2.hasScope('trust')")
     *             .antMatchers("/photos/**").access("#oauth2.denyOAuthClient() and hasRole('ROLE_USER') or #oauth2.hasScope('read')")
     *             .regexMatchers(HttpMethod.DELETE, "/oauth/users/([^/].*?)/tokens/.*").access("#oauth2.clientHasRole('ROLE_CLIENT') and (hasRole('ROLE_USER') or #oauth2.isClient()) and #oauth2.hasScope('write')")
     *             .regexMatchers(HttpMethod.GET, "/oauth/users/.*").access("#oauth2.clientHasRole('ROLE_CLIENT') and (hasRole('ROLE_USER') or #oauth2.isClient()) and #oauth2.hasScope('read')")
     *             .regexMatchers(HttpMethod.GET, "/oauth/clients/.*").access("#oauth2.clientHasRole('ROLE_CLIENT') and #oauth2.isClient() and #oauth2.hasScope('read')")
     *
     *         .and().requestMatchers().antMatchers("/photos/**", "/oauth/users/**", "/oauth/clients/**")
     *         .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
     *         .and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler())
     *
     *         // CSRF protection is awkward for machine clients
     *         .and().csrf().requireCsrfProtectionMatcher(new AntPathRequestMatcher("/oauth/**")).disable()
     *         .apply(new OAuth2ResourceServerConfigurer()).tokenStore(tokenStore).resourceId(SPARK_RESOURCE_ID);
     * }
     * </pre>
     *
     * @see org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurer#configure(HttpSecurity)
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            // 设置 /login 无需权限访问
            .antMatchers("/login").permitAll()
            .antMatchers("/callback").permitAll()
            // 设置其它请求，需要认证后访问
            .anyRequest().authenticated()
        ;
    }

    /**
     * Description: 为资源服务器配置特定属性, 如 resource-id.<br>
     * Details: 查看 {@link ResourceServerSecurityConfigurer} 的源代码可以知道, 默认情况下,
     * 已经为 {@link ResourceServerSecurityConfigurer} 注入了 {@link OAuth2WebSecurityExpressionHandler}
     *
     * @see org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurer#configure(ResourceServerSecurityConfigurer)
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID).stateless(true).tokenServices(remoteTokenServices());

        // ~ 指定 ResourceServerTokenServices
        resources.tokenServices(new CustomResourceServerTokenServices(jwtAccessTokenConverter()));

        // ~ AuthenticationEntryPoint. ref: OAuth2AuthenticationProcessingFilter
        resources.authenticationEntryPoint(authenticationEntryPoint);
    }

    /**
     * Description: 远端令牌服务类<br>
     * Details: 调用授权服务器的 /oauth/check_token 端点解析令牌. <br>
     * 在本 DEMO 中, 调用授权服务器的 {@link org.springframework.security.oauth2.provider.endpoint.CheckTokenEndpoint} 端点, <br>
     * 将私钥签名的 JWT 发到授权服务器, 后者用公钥验证 Signature 部分
     *
     * @return org.springframework.security.oauth2.provider.token.RemoteTokenServices
     */
    private RemoteTokenServices remoteTokenServices() {
        final RemoteTokenServices remoteTokenServices = new RemoteTokenServices();

        // ~ 设置 RestTemplate, 以自行决定异常处理
        final RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            // Ignore 400
            public void handleError(ClientHttpResponse response) throws IOException {
                final int rawStatusCode = response.getRawStatusCode();
                System.out.println(rawStatusCode);
                if (rawStatusCode != 400) {
                    final String responseData = new String(super.getResponseBody(response));
                    throw new OAuth2Exception(responseData);
                }
            }
        });
        remoteTokenServices.setRestTemplate(restTemplate);

        // ~ clientId 和 clientSecret 会以 base64(clientId:clientSecret) basic 方式请求授权服务器
     /*   remoteTokenServices.setClientId(RESOURCE_ID);
        remoteTokenServices.setClientSecret("resource-server-p");*/
        remoteTokenServices.setClientId(oAuth2ClientProperties.getClientId());
        remoteTokenServices.setClientSecret(oAuth2ClientProperties.getClientSecret());

        // ~ 请求授权服务器的 CheckTokenEndpoint 端点解析 JWT (AuthorizationServerEndpointsConfigurer 中指定的 tokenServices.
        //   实现了 ResourceServerTokenServices 接口,
        //   如果没有, 则使用默认的 (org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerEndpointsConfiguration.checkTokenEndpoint)
        remoteTokenServices.setCheckTokenEndpointUrl(resourceServerProperties.getTokenInfoUri());
        return remoteTokenServices;
    }

}