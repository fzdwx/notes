package cn.like.code.testCase.str;

import cn.hutool.core.date.DateUtil;

import java.util.concurrent.TimeUnit;

import static cn.like.code.testCase.Redis.reactive;

/**
 * .____    .__ __
 * |    |   |__|  | __ ____
 * |    |   |  |  |/ // __ \
 * |    |___|  |    <\  ___/
 * |_______ \__|__|_ \\___  >
 * \/       \/    \/
 * <p>
 * desc
 *
 * @author like
 * @date 2021-05-05 16:51
 */
public class 实现用户操作日志审计功能_05 {

    public static void main(String[] args) {
        String now = DateUtil.now();
        String key = "operation_log_"+ now;
        reactive().setnx(key, "").block();

        for (int i = 1; i < 10; i++) {
            reactive().append(key, "今天的第" + (i) + "条操作日志\n").block();
        }

        reactive().get(key).subscribe(System.out::println);

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
