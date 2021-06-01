package cn.like.code.testCase.list;

import cn.hutool.json.JSONUtil;
import io.lettuce.core.KeyValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static cn.like.code.testCase.Redis.reactive;

/**
 * .____    .__ __
 * |    |   |__|  | __ ____
 * |    |   |  |  |/ // __ \
 * |    |___|  |    <\  ___/
 * |_______ \__|__|_ \\___  >
 * \/       \/    \/
 *
 * 注册成功发送邮件
 *
 * @author like
 */
public class 实现网站用户注册时的邮件验证机制_13 {


    public static String KEY_SEND_MALL_TASK_QUEUE = "send_mall_task_queue";

    public static void main(String[] args) {


        实现网站用户注册时的邮件验证机制_13 test = new 实现网站用户注册时的邮件验证机制_13();

        for (int i = 0; i < 10; i++) {
            String userId = UUID.randomUUID().toString();
            String emailAddress = "98065920@qq.com";
            MallTask mallTask = new MallTask(userId, emailAddress);
            test.emailTaskEnQueue(mallTask).subscribe(s -> {
                System.out.println("s = " + s);
            });
        }

        test.emailTaskDeQueue().subscribe(s -> {
            System.out.println("s.getKey() = " + s.getKey());
            System.out.println("s.getValue() = " + s.getValue());
        });


        try {
            TimeUnit.SECONDS.sleep(7);
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
    public Mono<Long> emailTaskEnQueue(MallTask mallTask) {
        return reactive().lpush(KEY_SEND_MALL_TASK_QUEUE, JSONUtil.toJsonStr(mallTask));
    }

    public Mono<KeyValue<String, String>> emailTaskDeQueue() {
        return reactive().brpop(5000, KEY_SEND_MALL_TASK_QUEUE);
    }


    @AllArgsConstructor
    @Data
    static class MallTask {
        private String userId;
        private String emailAddress;
    }
}
