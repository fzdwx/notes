package cn.like.redis.testCase.set;

import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
public class 实现网站上的抽奖程序_19 {

    /**
     * 加抽奖的候选人
     *
     * @param topic 主题
     * @param user  用户
     * @return
     */
    public Mono<Long> addLotteryDrawCandidate(String topic, String user) {
        return reactive().sadd("lottery_draw:" + topic + ":candidate", user);
    }

    /**
     * 做抽奖
     *
     * @param topic 主题
     * @param count 数
     * @return {@link Mono<Set<String>>}
     */
    public Mono<Set<String>> doLotteryDraw(String topic, int count) {
        return reactive().srandmember("lottery_draw:" + topic + ":candidate", count).collect(Collectors.toSet());
    }

    public static void main(String[] args) {
        实现网站上的抽奖程序_19 test = new 实现网站上的抽奖程序_19();
        String topic = "随机抽取五位用户送MAC_PRO";
        for (int i = 1; i < 20; i++) {
            test.addLotteryDrawCandidate(topic, String.valueOf(i)).subscribe();
        }
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        test.doLotteryDraw(topic,5).subscribe(s->{
            System.out.println("幸运用户(获得mac pro):"+s);
        });

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
