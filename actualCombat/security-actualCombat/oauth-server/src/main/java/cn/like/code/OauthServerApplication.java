package cn.like.code;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * oauth server
 *
 * @author like
 * @date 2021/6/2 10:00
 */
@SpringBootApplication(scanBasePackages = {
        "cn.like.code.*"
})
public class OauthServerApplication {

    public static void main(String[] args) {
        final ConfigurableApplicationContext context = SpringApplication.run(OauthServerApplication.class, args);

    }
}
