
package cn.like.code.config.support.custom;

import cn.like.code.util.response.ResponseWrapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义访问被拒绝处理程序 {@link CustomAuthenticationEntryPoint}
 *
 * @author like
 * @date 2021/06/04
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        ResponseWrapper.unauthorizedResponse(response, accessDeniedException.getMessage());
    }
}