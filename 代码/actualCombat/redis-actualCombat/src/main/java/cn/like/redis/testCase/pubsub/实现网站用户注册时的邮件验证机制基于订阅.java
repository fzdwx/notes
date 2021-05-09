package cn.like.redis.testCase.pubsub;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.pubsub.api.reactive.RedisPubSubReactiveCommands;
import io.lettuce.core.resource.DefaultClientResources;
import lombok.AllArgsConstructor;
import lombok.Data;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * .____    .__ __
 * |    |   |__|  | __ ____
 * |    |   |  |  |/ // __ \
 * |    |___|  |    <\  ___/
 * |_______ \__|__|_ \\___  >
 * \/       \/    \/
 *
 * desc
 *
 * @author like
 */
public class 实现网站用户注册时的邮件验证机制基于订阅 {


    public static String CHANNEL_SEND_MALL_TASK = "send_mall_task_channel";
    public static final RedisClient REDIS_CLIENT_PUB;
    public static final RedisClient REDIS_CLIENT_SUB;

    public static final RedisPubSubReactiveCommands<String, String> sub;

    @AllArgsConstructor
    @Data
    static class MallTask {
        private String userId;
        private String emailAddress;

    }

    static {
        RedisURI uri = RedisURI.builder()
                .withHost("localhost")
                .withPort(6379)
                .withTimeout(Duration.of(10, ChronoUnit.SECONDS))
                .build();
        DefaultClientResources.Builder builder = DefaultClientResources.builder();
        REDIS_CLIENT_PUB = RedisClient.create(builder.build(), uri);
        REDIS_CLIENT_SUB = RedisClient.create(builder.build(), uri);

        // 订阅通道
        sub = REDIS_CLIENT_SUB.connectPubSub().reactive();
    }

    public static void main(String[] args) {
        实现网站用户注册时的邮件验证机制基于订阅 test = new 实现网站用户注册时的邮件验证机制基于订阅();

        sub.subscribe(CHANNEL_SEND_MALL_TASK).subscribe();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                String userId = UUID.randomUUID().toString();

                String emailAddress = RandomUtil.randomNumbers(9) + "@qq.com";
                MallTask mallTask = new MallTask(userId, emailAddress);
                test.produceEmailTask(mallTask).block();
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        test.consumeEmailTask();
       /* new Thread(() -> {

        }).start();*/

        try {
            TimeUnit.SECONDS.sleep(20);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 电子邮件任务 入队
     *
     * @param mallTask 购物中心的任务
     * @return {@link Mono<Long>}
     */
    public Mono<Long> produceEmailTask(MallTask mallTask) {
        return REDIS_CLIENT_PUB.connectPubSub().reactive().publish(CHANNEL_SEND_MALL_TASK, JSONUtil.toJsonStr(mallTask));
    }

    /**
     * 消费电子邮件任务
     */
    public void consumeEmailTask() {
        sub.observeChannels().subscribe(message -> {
            MallTask task = JSONUtil.toBean(message.getMessage(), MallTask.class);
            // send mail task.getEmailAddress()

            System.out.println("发送成功: " + task.getEmailAddress());
        });
    }
}
