package cn.like.code.config.support.runner;

import cn.like.code.entity.dto.ClientAccessScopeResourceAddressMapping;
import cn.like.code.entity.dto.ClientAuthorityResourceAddressMapping;
import cn.like.code.entity.dto.AdminAuthorityResourceAddressMapping;
import cn.like.code.mapper.AdminAuthorityMapper;
import cn.like.code.mapper.ClientAccessScopeMapper;
import cn.like.code.mapper.ClientAuthorityMapper;
import cn.like.code.redis.service.RedisService;
import cn.like.code.redis.RedisKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * desc: 资源地址元数据初始化<br>
 * details: 授权服务器启动的时候, 从数据源里加载访问控制香瓜你的元数据, 并放入缓存. 供资源服务器调用
 *
 * @author: like
 * @since: 2021/6/5 9:08
 * @email: 980650920@qq.com
 */
@Component
@Slf4j
@Order(1)
public class ResourceAddressMetadataInitializer implements ApplicationRunner {

    private RedisService redisService;

    // ~ ResourceAddress Mappers
    // -----------------------------------------------------------------------------------------------------------------

    private ClientAccessScopeMapper clientAccessScopeMapper;

    private ClientAuthorityMapper clientAuthorityMapper;

    private AdminAuthorityMapper adminAuthorityMapper;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("[Resource address metadata initializing ...]");

        redisService.hset( // 缓存所有客户端的 scope
                // key : cache:metadata.resource-address:client-access-scope
                RedisKey.CACHE_PREFIX_METADATA_RESOURCE_ADDRESS
                        .buildKey(ClientAccessScopeResourceAddressMapping.CACHE_SUFFIX),
                // map : scope :  端点名@资源 ID
                clientAccessScopeMapper.composeClientAccessScopeResourceAddressMapping().stream().collect(Collectors.toMap(
                        ClientAccessScopeResourceAddressMapping::getClientAccessScopeName,
                        ClientAccessScopeResourceAddressMapping::getResourceAddress
                )));
        log.debug("==============================================================================");
        log.debug("|            Metadata: ClientAccessScope - ResourceAddress Cached            |");
        log.debug("==============================================================================");

        redisService.hset( // 缓存所有客户端的 Authority
                // key : cache:metadata.resource-address:client-authority
                RedisKey.CACHE_PREFIX_METADATA_RESOURCE_ADDRESS
                        .buildKey(ClientAuthorityResourceAddressMapping.CACHE_SUFFIX),
                // val
                clientAuthorityMapper.composeClientAuthorityResourceAddressMapping().stream().collect(Collectors.toMap(
                        ClientAuthorityResourceAddressMapping::getClientAuthorityName,
                        ClientAuthorityResourceAddressMapping::getResourceAddress
                )));
        log.debug("==============================================================================");
        log.debug("|             Metadata: ClientAuthority - ResourceAddress Cached             |");
        log.debug("==============================================================================");

        redisService.hset( // 缓存所有admin的 Authority
                // key : cache:metadata.resource-address:admin-authority
                RedisKey.CACHE_PREFIX_METADATA_RESOURCE_ADDRESS
                        .buildKey(AdminAuthorityResourceAddressMapping.CACHE_SUFFIX),
                // val
                adminAuthorityMapper.composeUserAuthorityResourceAddressMapping().stream().collect(Collectors.toMap(
                        AdminAuthorityResourceAddressMapping::getAdminAuthorityName,
                        AdminAuthorityResourceAddressMapping::getResourceAddress
                )));
        log.debug("==============================================================================");
        log.debug("|              Metadata: UserAuthority - ResourceAddress Cached              |");
        log.debug("==============================================================================");

        log.info("Resource address metadata initialized.");
    }


    @Autowired
    public void setRedisService(RedisService redisService) {
        this.redisService = redisService;
    }

    @Autowired
    public void setClientAccessScopeMapper(ClientAccessScopeMapper clientAccessScopeMapper) {
        this.clientAccessScopeMapper = clientAccessScopeMapper;
    }

    @Autowired
    public void setClientAuthorityMapper(ClientAuthorityMapper clientAuthorityMapper) {
        this.clientAuthorityMapper = clientAuthorityMapper;
    }

    @Autowired
    public void setUserAuthorityMapper(AdminAuthorityMapper adminAuthorityMapper) {
        this.adminAuthorityMapper = adminAuthorityMapper;
    }
}
