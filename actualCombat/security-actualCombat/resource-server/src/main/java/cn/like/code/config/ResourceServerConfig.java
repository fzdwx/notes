package cn.like.code.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.OAuth2ClientProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

/**
 * 资源服务器配置
 *
 * @author pdd20
 * @date 2021/06/03
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Value("${spring.application.name}")
    private String RESOURCE_ID;
    @Autowired
    private ResourceServerProperties resourceServerProperties;
    @Autowired
    private OAuth2ClientProperties oAuth2ClientProperties;
    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

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