package cn.like.code.config.support.accesscontrol;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

/**
 * 配置 {@link FilterSecurityInterceptor}
 *
 * @author: like
 * @since: 2021/6/5 11:24
 * @email: 980650920@qq.com
 * @desc:
 */
public class FilterSecurityInterceptorPostProcessor implements ObjectPostProcessor<FilterSecurityInterceptor> {

    private final AccessDecisionManager accessDecisionManager;
    private final FilterInvocationSecurityMetadataSource securityMetadataSource;

    public FilterSecurityInterceptorPostProcessor(AccessDecisionManager accessDecisionManager, FilterInvocationSecurityMetadataSource securityMetadataSource) {
        this.accessDecisionManager = accessDecisionManager;
        this.securityMetadataSource = securityMetadataSource;
    }

    @Override
    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
        o.setSecurityMetadataSource(securityMetadataSource);
        o.setAccessDecisionManager(accessDecisionManager);
        return o;
    }
}
