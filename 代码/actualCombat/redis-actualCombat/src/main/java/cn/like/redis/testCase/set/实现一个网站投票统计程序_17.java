package cn.like.redis.testCase.set;

import cn.hutool.core.util.RandomUtil;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static cn.like.redis.testCase.Redis.reactive;

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
public class 实现一个网站投票统计程序_17 {

    private String KEY_VOTE_TOPIC(String topic) {
        return "vote_topic:" + topic;
    }

    public Mono<Long> vote(String user, String topic) {
        return reactive().sadd(KEY_VOTE_TOPIC(topic), user);
    }

    public Mono<Boolean> hasVote(String user, String topic) {
        return reactive().sismember(KEY_VOTE_TOPIC(topic), user);
    }

    public Mono<Long> voteCount(String topic) {
        return reactive().scard(KEY_VOTE_TOPIC(topic));
    }

    public static void main(String[] args) {
        List<String> topics = Arrays.asList("like 很帅", "like 不帅");

        实现一个网站投票统计程序_17 test = new 实现一个网站投票统计程序_17();
        for (int i = 0; i < 100000; i++) {
            int chose = RandomUtil.randomInt(0, 2);

            test.vote(String.valueOf(i), topics.get(chose)).subscribe();

        }

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        test.voteCount(topics.get(0)).subscribe(s -> {
            System.out.println(topics.get(0) + " : " + s);
        });

        test.voteCount(topics.get(1)).subscribe(s -> {
            System.out.println(topics.get(1) + " : " + s);
        });

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
