package cn.like.code.testCase.set;

import cn.hutool.core.date.DatePattern;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;
import java.util.Date;
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
 * 用户访问量
 *
 * @author like
 */
public class 网站每日UV数据指标去重统计_14 {

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(DatePattern.CHINESE_DATE_PATTERN);
    public static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat(DatePattern.CHINESE_DATE_TIME_PATTERN);

    private static final String KEY_USER_ACCESS_COUNT_SET = "USER_ACCESS_COUNT_SET:" + DATE_FORMAT.format(new Date());

    public Mono<Long> addUserAccess(String... userIds) {
        return reactive().sadd(KEY_USER_ACCESS_COUNT_SET, userIds);
    }

    public Long viewUserAccessCount() {
        return reactive().scard(KEY_USER_ACCESS_COUNT_SET).block();
    }

    public static void main(String[] args) {
        网站每日UV数据指标去重统计_14 test = new 网站每日UV数据指标去重统计_14();
        for (int i = 1; i <= 100; i++) {
            for (int j = 0; j < 10; j++) {
                test.addUserAccess(String.valueOf(i)).block();
            }
        }

        System.out.println("uv:" + test.viewUserAccessCount());

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
