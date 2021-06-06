package cn.like.code.config.support.custom.client;

import cn.hutool.json.JSONUtil;
import cn.like.code.entity.dto.ClientDTO;
import cn.like.code.redis.service.RedisService;
import cn.like.code.service.ClientService;
import cn.like.code.redis.RedisKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 自定义的 {@link ClientDetailsService}
 *
 * @author LiKe
 * @version 1.0.0
 * @date 2020-06-15 12:41
 */
@Slf4j
@Service
public class CustomClientDetailsService implements ClientDetailsService {

    private ClientService clientService;

    private RedisService redisService;

    /**
     * Description: 从数据库中获取已经注册过的客户端信息<br>
     * Details: 该方法会在整个认证过程中被多次调用, 所以应该缓存. 缓存过期时间在 access_token 有效期的基础上加一个时间 buffer
     *
     * @param client 客户端
     * @see ClientDetailsService#loadClientByClientId(String)
     */
    @Override
    public ClientDetails loadClientByClientId(String client) throws ClientRegistrationException {
        log.debug("About to produce ClientDetails with client: {}", client);

        final String key = RedisKey.CACHE_AUTH_CLIENT_ID.buildKey(client);

        // 先从缓存中获取 ClientDto
        ClientDTO clientDto = redisService.get(key, ClientDTO.class);
        // 如果缓存中没有, 从数据库查询并置入缓存
        if (Objects.isNull(clientDto)) {
            clientDto = clientService.getClient(client);

            if (Objects.isNull(clientDto)) {
                throw new ClientRegistrationException(String.format("客户端 %s 尚未注册!", client));
            }
            // Buffer: 10s
            redisService.setEX(key, JSONUtil.toJsonStr(clientDto), (long) (clientDto.getAccessTokenValidity() + 10));
        }

        return new CustomClientDetails(clientDto);
    }

    // ~ Autowired
    // -----------------------------------------------------------------------------------------------------------------

    @Autowired
    public void setClientMapper(ClientService clientService) {
        this.clientService = clientService;
    }

    @Autowired
    public void setRedisService(RedisService redisService) {
        this.redisService = redisService;
    }
}