package cn.like.code.config;

import cn.like.code.config.support.custom.token.JwtTokenEnhancer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

/**
 * 使用Jwt存储token的配置
 *
 * @author like
 * @date 2021/6/3 16:16
 */
@Configuration
public class JwtTokenStoreConfig {

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * jwt访问令牌转换器
     *
     * @return {@link JwtAccessTokenConverter}
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        // final KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("authorization-server.jks"), "rootroot".toCharArray());
        final JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        // jwtAccessTokenConverter.setKeyPair(keyStoreKeyFactory.getKeyPair("authorization-server-jwt-keypair"));
        jwtAccessTokenConverter.setSigningKey(secret); // JWT 秘钥
        return jwtAccessTokenConverter;
    }

    /**
     * jwt令牌存储
     *
     * @return {@link JwtTokenStore}
     */
    @Bean
    public JwtTokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    /**
     * jwt token 增强
     *
     * @return {@link JwtTokenEnhancer}
     */
    @Bean
    public JwtTokenEnhancer jwtTokenEnhancer() {
        return new JwtTokenEnhancer();
    }
}
