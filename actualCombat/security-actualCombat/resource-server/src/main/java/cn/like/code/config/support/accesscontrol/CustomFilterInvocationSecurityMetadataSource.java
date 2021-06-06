package cn.like.code.config.support.accesscontrol;

import cn.hutool.core.map.MapUtil;
import cn.like.code.entity.dto.AdminAuthorityResourceAddressMapping;
import cn.like.code.entity.dto.ClientAccessScopeResourceAddressMapping;
import cn.like.code.entity.dto.ClientAuthorityResourceAddressMapping;
import cn.like.code.redis.service.RedisService;
import cn.like.code.redis.RedisKey;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static cn.hutool.core.text.StrPool.AT;

/**
 * Description: 自定义的 {@link FilterInvocationSecurityMetadataSource}<br>
 * Details: SecurityMetadataSource 的提供的 Configuration Attributes 正是 AccessDecisionManager 的判断依据
 * (ref: org.springframework.security.access.intercept.AbstractSecurityInterceptor#beforeInvocation(Object))
 * 主要作用: 将存储在redis 中的(由ResourceAddressMetadataInitializer) 存放的 授权信息取出 封装成Collection<ConfigAttribute>
 *
 * @author: like
 * @since: 2021/6/5 11:26
 * @email: 980650920@qq.com
 * @desc:
 */
@Slf4j
@Component
public class CustomFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    /**
     * {@link ConfigAttribute} 配置属性的前缀: 客户端访问范围
     */
    public static final String CONFIG_ATTR_PREFIX_CLIENT_ACCESS_SCOPE = ClientAccessScopeResourceAddressMapping.CACHE_SUFFIX + AT;

    /**
     * {@link ConfigAttribute} 配置属性的前缀: 客户端职权
     */
    public static final String CONFIG_ATTR_PREFIX_CLIENT_AUTHORITY = ClientAuthorityResourceAddressMapping.CACHE_SUFFIX + AT;

    /**
     * {@link ConfigAttribute} 配置属性的前缀: 用户端职权
     */
    public static final String CONFIG_ATTR_PREFIX_ADMIN_AUTHORITY = AdminAuthorityResourceAddressMapping.CACHE_SUFFIX + AT;

    /**
     * 资源服务 ID
     */
    @Setter
    private String resourceId;

    private RedisService redisService;

    /**
     * 返回 所有 匹配 endpoint的权限信息且后缀和resourceId相同
     *
     * @param o o
     * @return {@link Collection<ConfigAttribute>}
     * @throws IllegalArgumentException 非法参数异常
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        final Collection<ConfigAttribute> configAttributes;

        final FilterInvocation iv = (FilterInvocation) o;
        // eg. /resource/access
        final String endpoint = iv.getRequestUrl();
        // 资源地址 eg. /resource/access@resourceId
        final String resourceAddress = StringUtils.join(endpoint, AT, Objects.requireNonNull(resourceId, "资源服务器 ID 未定义!"));

        // ~ 通过要访问的端点和当前资源服务器 ID 获取可访问当前资源的 ClientAuthority, UserAuthority 和 ClientAccessScope 集合,
        //   约定, 每一种权限按照约定的前缀放入集合, 便于 AccessDecisionManager.
        //   然后, AccessDecisionManager 根据 OAuth2Authentication 判断 authorities / scopes 是否在集合中
        // ~~~

        // client scopeName
        final Map<String, String> clientAccessScopeResourceAddressMapping = redisService.hgetall(
                RedisKey.CACHE_PREFIX_METADATA_RESOURCE_ADDRESS.buildKey(ClientAccessScopeResourceAddressMapping.CACHE_SUFFIX));
        configAttributes = clientAccessScopeResourceAddressMapping.keySet()
                .stream()
                .filter(scopeName -> StringUtils.equals(MapUtil.getStr(clientAccessScopeResourceAddressMapping, scopeName), resourceAddress))
                .map(scopeName -> new SecurityConfig(StringUtils.join(CONFIG_ATTR_PREFIX_CLIENT_ACCESS_SCOPE, scopeName)))
                .collect(Collectors.toSet());

        // client authority
        final Map<String, String> clientAuthorityResourceAddressMapping = redisService.hgetall(
                RedisKey.CACHE_PREFIX_METADATA_RESOURCE_ADDRESS.buildKey(ClientAuthorityResourceAddressMapping.CACHE_SUFFIX));
        configAttributes.addAll(clientAuthorityResourceAddressMapping.keySet()
                .stream()
                .filter(authorityName -> StringUtils.equals(MapUtil.getStr(clientAuthorityResourceAddressMapping, authorityName), resourceAddress))
                .map(authorityName -> new SecurityConfig(StringUtils.join(CONFIG_ATTR_PREFIX_CLIENT_AUTHORITY, authorityName)))
                .collect(Collectors.toSet()));

        // admin authority
        final Map<String, String> adminAuthorityResourceAddressMapping = redisService.hgetall(
                RedisKey.CACHE_PREFIX_METADATA_RESOURCE_ADDRESS.buildKey(AdminAuthorityResourceAddressMapping.CACHE_SUFFIX));
        configAttributes.addAll(adminAuthorityResourceAddressMapping.keySet()
                .stream()
                .filter(authorityName -> StringUtils.equals(MapUtil.getStr(adminAuthorityResourceAddressMapping, authorityName), resourceAddress))
                .map(authorityName -> new SecurityConfig(StringUtils.join(CONFIG_ATTR_PREFIX_ADMIN_AUTHORITY, authorityName)))
                .collect(Collectors.toSet()));

        // ~ 为 AccessDecisionManager 提供包含匹配当前访问的资源端点的 ClientAuthority, UserAuthority, 以及 ClientAccessScope 的集合
        //   格式:
        //       - ClientAccessScope: ClientAccessScopeResourceAddressMapping.CACHE_PREFIX@ClientAccessScopeName
        //       - ClientAuthority: ClientAuthorityResourceAddressMapping.CACHE_PREFIX@ClientAuthorityName
        //       - AdminAuthority: AdminAuthorityResourceAddressMapping.CACHE_PREFIX@UserAuthorityName
        return configAttributes;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        log.debug("CustomFilterInvocationSecurityMetadataSource :: getAllConfigAttributes");
        throw new UnsupportedOperationException("不支持的操作!");
    }

    @Override
    public boolean supports(Class<?> clazz) {
        log.debug("CustomFilterInvocationSecurityMetadataSource :: supports :: {}", clazz.getCanonicalName());
        // ~ FilterInvocation: 持有与 HTTP 过滤器相关的对象
        return FilterInvocation.class.isAssignableFrom(clazz);
    }

    @Autowired
    public void setRedisService(RedisService redisService) {
        this.redisService = redisService;
    }
}
