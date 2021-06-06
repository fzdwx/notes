package cn.like.code.redis;

import cn.like.code.redis.config.LettuceConnectionConfiguration;
import cn.like.code.redis.config.LettucePoolConfiguration;
import cn.like.code.redis.properties.LettuceConfigProperties;
import cn.like.code.redis.service.RedisService;
import cn.like.code.redis.service.impl.RedisClusterServiceImpl;
import cn.like.code.redis.service.impl.RedisServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * desc: 使用spring.factories 自动配置
 *
 * @author: like
 * @since: 2021/6/6 11:15
 * @email: 980650920@qq.com
 */
// @EnableAutoConfiguration
@EnableConfigurationProperties(LettuceConfigProperties.class)
@Import({LettuceConnectionConfiguration.class, LettucePoolConfiguration.class})
public class RedisAutoConfigure {

    @Bean
    @ConditionalOnProperty(value = "redis.fire", havingValue = "false")
    public RedisService redisServiceImpl() {
        return new RedisServiceImpl();
    }

    @Bean
    @ConditionalOnProperty(value = "redis.fire", havingValue = "true")
    public RedisService redisClusterServiceImpl() {
        return new RedisClusterServiceImpl();
    }
}
