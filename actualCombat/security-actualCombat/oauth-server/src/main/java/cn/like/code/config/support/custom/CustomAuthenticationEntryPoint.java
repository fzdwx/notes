package cn.like.code.config.support.custom;


import cn.like.code.config.AuthorizationServerConfig;
import cn.like.code.config.SecurityConfig;
import cn.like.code.util.response.ResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 自定义的身份验证入口点  {@link AuthenticationEntryPoint}
 *
 * @author like
 * @date 2021/06/04
 */
@Component
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final RequestMatcher authorizationCodeGrantRequestMatcher = new AuthorizationCodeGrantRequestMatcher();

    private final AuthenticationEntryPoint loginUrlAuthenticationEntryPoint = new LoginUrlAuthenticationEntryPoint(SecurityConfig.DEFAULT_LOGIN_URL);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.debug("Custom AuthenticationEntryPoint triggered with exception: {}.", authException.getClass().getCanonicalName());

        // 触发重定向到登陆页面
        if (authorizationCodeGrantRequestMatcher.matches(request)) {
            loginUrlAuthenticationEntryPoint.commence(request, response, authException);
            return;
        }
        ResponseWrapper.forbiddenResponse(response,authException.getMessage());
    }

    private static class AuthorizationCodeGrantRequestMatcher implements RequestMatcher {

        /**
         * <ol>
         *     <li>授权码模式 URI</li>
         *     <li>隐式授权模式 URI</li>
         * </ol>
         */
        private static final Set<String> SUPPORT_URIS = new HashSet<>(Arrays.asList("response_type=code", "response_type=token"));

        @Override
        public boolean matches(HttpServletRequest request) {

            if (StringUtils.equals(request.getServletPath(), AuthorizationServerConfig.OAUTH_AUTHORIZE_ENDPOINT)) {
                final String queryString = request.getQueryString();
                return SUPPORT_URIS.stream().anyMatch(supportUri -> StringUtils.indexOf(queryString, supportUri) != StringUtils.INDEX_NOT_FOUND);
            }
            return false;
        }
    }
}
