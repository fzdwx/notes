package cn.like.code;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author like
 * @date 2021/6/3 9:57
 */
@SpringBootApplication(scanBasePackages = {
        "cn.like.code.*"
},exclude = RedisAutoConfiguration.class)
public class ResourceApplication {

    public static void main(String[] args) {
        final ConfigurableApplicationContext context = SpringApplication.run(ResourceApplication.class, args);

    }
}
