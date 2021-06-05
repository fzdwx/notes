package cn.like.code.config.support.token;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * 自定义资源服务器令牌服务
 * <pre>
 *     实现认证服务器解耦，认证服务办法 authorization 后，由各个资源服务器自行解析
 * </pre>
 * @author: like
 * @since: 2021/6/4 21:15
 * @email: 980650920@qq.com
 * @desc:
 */
@Slf4j
public class CustomResourceServerTokenServices implements ResourceServerTokenServices {

    private final TokenStore tokenStore;

    public CustomResourceServerTokenServices(JwtAccessTokenConverter accessTokenConverter) {
        this.tokenStore = new JwtTokenStore(accessTokenConverter);
    }

    /**
     * Description: 用于从 accessToken 中加载凭证信息, 并构建出 {@link OAuth2Authentication} 的方法<br>
     *     Details:
     *
     * @see ResourceServerTokenServices#loadAuthentication(String)
     */
    @Override
    public OAuth2Authentication loadAuthentication(String accessToken) throws AuthenticationException, InvalidTokenException {
        log.debug("CustomResourceServerTokenServices :: loadAuthentication called ...");
        log.trace("CustomResourceServerTokenServices :: loadAuthentication :: accessToken: {}", accessToken);

        return tokenStore.readAuthentication(accessToken);
    }

    @Override
    public OAuth2AccessToken readAccessToken(String accessToken) {
        log.debug("CustomResourceServerTokenServices :: readAccessToken called ...");
        throw new UnsupportedOperationException("暂不支持 readAccessToken!");
    }

}
