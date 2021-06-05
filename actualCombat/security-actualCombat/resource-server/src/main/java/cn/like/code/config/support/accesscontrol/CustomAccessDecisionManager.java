package cn.like.code.config.support.accesscontrol;


import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;


import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static cn.hutool.core.text.StrPool.AT;

/**
 * 自定义的 {@link AccessDecisionManager} AccessDecisionManager 用于提供最终的访问决策控制.
 *
 * @author: like
 * @since: 2021/6/5 12:08
 * @email: 980650920@qq.com
 * @desc:
 */
@Slf4j
@Component
public class CustomAccessDecisionManager implements AccessDecisionManager {

    /**
     * 第一方客户端前端 CLIENT_AUTHORITY 名称
     */
    public static final String CLIENT_AUTHORITY_FIRST_PARTY_FRONTEND_CLIENT = "FIRST_PARTY_FRONTEND_CLIENT";

    // =================================================================================================================

    private static final String CONFIG_ATTR_PREFIX_CLIENT_AUTHORITY = CustomFilterInvocationSecurityMetadataSource.CONFIG_ATTR_PREFIX_CLIENT_AUTHORITY;
    private static final String CONFIG_ATTR_PREFIX_CLIENT_ACCESS_SCOPE = CustomFilterInvocationSecurityMetadataSource.CONFIG_ATTR_PREFIX_CLIENT_ACCESS_SCOPE;
    private static final String CONFIG_ATTR_PREFIX_ADMIN_AUTHORITY = CustomFilterInvocationSecurityMetadataSource.CONFIG_ATTR_PREFIX_ADMIN_AUTHORITY;

    // =================================================================================================================

    /**
     * 资源服务 ID
     */
    private String resourceId;


    /**
     * Description: 实现该资源服务的访问控制<br>
     * <dl>
     *     <dt>Access control principles:</dt>
     *     <dd>{@link OAuth2Authentication#isClientOnly()}{@code = true}: 验证客户端权限</dd>
     *     <dd>
     *         {@link OAuth2Authentication#isClientOnly()}{@code = false}: <br>
     *             - 如果是第一方客户端前端 ({@code CLIENT_AUTHORITY_FIRST_PARTY_FRONTEND_CLIENT}), 校验用户权限;<br>
     *             - 如果是第三方应用, 校验用户全和客户端的权限;
     *     </dd>
     * </dl>
     *
     * @param authentication   {@link org.springframework.security.oauth2.provider.OAuth2Authentication}
     * @param configAttributes 由 {@link CustomFilterInvocationSecurityMetadataSource} 组织的资源标识:
     *                         - ClientAccessScope: ClientAccessScopeResourceAddressMapping.CACHE_PREFIX@ClientAccessScopeName<br>
     *                         - ClientAuthority: ClientAuthorityResourceAddressMapping.CACHE_PREFIX@ClientAuthorityName<br>
     *                         - AdminAuthority: AdminAuthorityResourceAddressMapping.CACHE_PREFIX@UserAuthorityName<br>
     * @see AccessDecisionManager#decide(Authentication, Object, Collection)
     * @see CustomFilterInvocationSecurityMetadataSource#getAttributes(Object)
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        log.debug("Access controller :: start ...");

        final OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) authentication;
        final FilterInvocation filterInvocation = (FilterInvocation) object;
        final String resourceAddress =
                StringUtils.join(filterInvocation.getRequestUrl(), AT, Objects.requireNonNull(resourceId, "资源服务器 ID 未定义!"));

        final boolean clientOnly = oAuth2Authentication.isClientOnly();
        final String principalName = oAuth2Authentication.getName();
        final Set<String> metadataSource = configAttributes.stream().map(ConfigAttribute::getAttribute).collect(Collectors.toSet());

        if (clientOnly) {
            log.info("Access controller :: 请求来自第一方客户端 ...");
            final Set<String> clientAuthorities = oAuth2Authentication.getOAuth2Request().getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
            if (metadataSource.stream()
                    // 要求权限列表是 client-authority@ 开始的
                    .filter(configAttrStr -> StringUtils.startsWith(configAttrStr, CONFIG_ATTR_PREFIX_CLIENT_AUTHORITY))
                    // clientAuthorities 中存放的是当前所拥有的权限 最后
                    .noneMatch(filteredConfigAttrStr -> CollectionUtils.containsAny(clientAuthorities,
                            StringUtils.substring(filteredConfigAttrStr, CONFIG_ATTR_PREFIX_CLIENT_AUTHORITY.length())))
            ) {
                throw new InsufficientAuthenticationException(String.format("Access controller :: denied :: (客户端: %s) 没有足够的权限访问该资源: %s", principalName, resourceAddress));
            }
        } else {
            log.info("Access controller :: 请求可能来自第一方前端 ...");
            final Set<String> userAuthorities = oAuth2Authentication.getUserAuthentication().getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());

            // ~ 校验用户权限
            if (metadataSource.stream()
                    .filter(configAttrStr -> StringUtils.startsWith(configAttrStr, CONFIG_ATTR_PREFIX_ADMIN_AUTHORITY))
                    .noneMatch(filteredConfigAttrStr -> CollectionUtils.containsAny(userAuthorities,
                            StringUtils.substring(filteredConfigAttrStr, CONFIG_ATTR_PREFIX_ADMIN_AUTHORITY.length())))
            ) {
                throw new InsufficientAuthenticationException(String.format("Access controller :: denied :: (用户: %s) 没有足够的权限访问该资源: %s", principalName, resourceAddress));
            }

            if (!CollectionUtils.containsAny(userAuthorities, CLIENT_AUTHORITY_FIRST_PARTY_FRONTEND_CLIENT)) {
                log.info("Access controller :: 请求来自第三方客户端 ...");
                final Set<String> clientScopeNames = oAuth2Authentication.getOAuth2Request().getScope();
                // ~ 第三方应用: 还需要客户端 SCOPE
                if (metadataSource.stream()
                        .filter(configAttrStr -> StringUtils.startsWith(configAttrStr, CONFIG_ATTR_PREFIX_CLIENT_ACCESS_SCOPE))
                        .noneMatch(filteredConfigAttrStr -> CollectionUtils.containsAny(clientScopeNames, StringUtils.substring(filteredConfigAttrStr, CONFIG_ATTR_PREFIX_CLIENT_ACCESS_SCOPE.length())))
                ) {
                    throw new InsufficientAuthenticationException(String.format("Access controller :: denied :: (客户端: %s) 的方位范围不包括资源: %s", principalName, resourceAddress));
                }
            }
        }
        log.info("Access controller :: allowed");
    }


    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        log.debug("CustomFilterInvocationSecurityMetadataSource :: supports :: {}", clazz.getCanonicalName());
        // ~ FilterInvocation: 持有与 HTTP 过滤器相关的对象
        return FilterInvocation.class.isAssignableFrom(clazz);
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }
}
