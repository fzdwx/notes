package cn.like.redis.testCase.haperloglog;

import java.text.SimpleDateFormat;
import java.util.Date;

import static cn.like.redis.testCase.Redis.sync;

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
public class 基于HyperLogLog的网站UV统计程序26 {

    public void accessWeb(String user, String today) {
        sync().pfadd("user:access:" + today, user);
    }

    public static void main(String[] args) {
        String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        基于HyperLogLog的网站UV统计程序26 test = new 基于HyperLogLog的网站UV统计程序26();
        for (int i = 0; i < 1323; i++) {
            test.accessWeb(String.valueOf((i + 1)), today);
        }

        System.out.println("今天的uv：" + sync.pfcount("user:access:" + today));
    }
}
