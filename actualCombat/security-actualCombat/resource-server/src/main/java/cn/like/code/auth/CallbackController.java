package cn.like.code.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.OAuth2ClientProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/**
 * 回调控制器
 * 使用 授权码模式，提供 /callback 回调地址，在获取到授权码时，请求授权服务器，通过授权码获取访问令牌
 * @author like
 * @date 2021/06/03
 */
@RestController
@RequestMapping("/")
public class CallbackController {

    @Autowired
    private OAuth2ClientProperties oauth2ClientProperties;

    @Value("${security.oauth2.access-token-uri}")
    private String accessTokenUri;

    @GetMapping("/callback")
    public ResponseEntity<?> login(@RequestParam("code") String code) {
        final OAuth2AccessToken tokenInfo;
        try {
            // 创建 AuthorizationCodeResourceDetails 对象
            AuthorizationCodeResourceDetails resourceDetails = new AuthorizationCodeResourceDetails();
            resourceDetails.setAccessTokenUri(accessTokenUri);
            resourceDetails.setClientId(oauth2ClientProperties.getClientId());
            resourceDetails.setClientSecret(oauth2ClientProperties.getClientSecret());
            // 创建 OAuth2RestTemplate 对象
            OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(resourceDetails);
            restTemplate.getOAuth2ClientContext().getAccessTokenRequest().setAuthorizationCode(code); // <1> 设置 code
            restTemplate.getOAuth2ClientContext().getAccessTokenRequest().setPreservedState("http://127.0.0.1:9090/callback"); // <2> 通过这个方式，设置 redirect_uri 参数
            restTemplate.setAccessTokenProvider(new AuthorizationCodeAccessTokenProvider());
            // 获取访问令牌
            tokenInfo = restTemplate.getAccessToken();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(tokenInfo);
    }

}