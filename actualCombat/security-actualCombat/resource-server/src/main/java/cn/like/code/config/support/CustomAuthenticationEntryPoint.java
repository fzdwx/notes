package cn.like.code.config.support;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONException;
import cn.hutool.json.JSONUtil;
import cn.like.code.util.response.ResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.autoconfigure.cloudfoundry.SecurityResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        log.debug("Custom AuthenticationEntryPoint triggered with exception: {}.", authException.getClass().getCanonicalName());

        // 原始异常信息
        final String authExceptionMessage = authException.getMessage();

        try {
            final SecurityResponse securityResponse = JSONUtil.toBean(authExceptionMessage, SecurityResponse.class);
            ResponseWrapper.wrapResponse(response, securityResponse);
        } catch (JSONException ignored) {
            ResponseWrapper.forbiddenResponse(response, authExceptionMessage);
        }
    }
}
