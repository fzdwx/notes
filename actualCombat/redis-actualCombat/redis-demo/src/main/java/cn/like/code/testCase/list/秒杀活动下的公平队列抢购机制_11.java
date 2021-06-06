package cn.like.code.testCase.list;

import cn.hutool.core.util.StrUtil;
import reactor.core.publisher.Mono;

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
 * 公平队列 先入先出
 *
 * @author like
 */
public class 秒杀活动下的公平队列抢购机制_11 {

    /**
     * 产生秒杀请求
     *
     * @param secKillRequest 秒杀请求
     * @return {@link Mono<Long>}
     */
    public Mono<Long> produceSecLKillRequest(String secKillRequest) {
        return reactive().lpush("sec_kill_request", secKillRequest);
    }

    /**
     * 获取秒杀请求
     *
     * @return {@link Mono<String>}
     */
    public Mono<String> consumeSecKillRequest() {
        return reactive().rpop("sec_kill_request");
    }

    public static void main(String[] args) {
        秒杀活动下的公平队列抢购机制_11 test = new 秒杀活动下的公平队列抢购机制_11();
        for (int i = 0; i < 10; i++) {
            test.produceSecLKillRequest(String.format("第%s个秒杀请求", i + 1)).block();
        }

        for (int i = 0; i < 10; i++) {
            test.consumeSecKillRequest().subscribe(req -> {
                if (StrUtil.isBlank(req)) {
                    throw new RuntimeException();
                }
                System.out.println("req = " + req);
            });
        }
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
