package cn.like.redis.testCase.bitmap;

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
public class 基于位图的网站用户行为记录程序 {
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 记录用户操作日志
     *
     * @param user      用户
     * @param operation 操作
     */
    public void recordUserOperationLog(Long user, String operation) {
        String today = SIMPLE_DATE_FORMAT.format(new Date());

        sync().setbit("operation:" + operation + ":" + today + ":log", user, 1);
    }

    public Long doesOperation(Long user, String operation) {
        String today = SIMPLE_DATE_FORMAT.format(new Date());
        return sync.getbit("operation:" + operation + ":" + today + ":log", user);
    }

    public static void main(String[] args) {
        基于位图的网站用户行为记录程序 test = new 基于位图的网站用户行为记录程序();

        String operation = "签到";
        test.recordUserOperationLog(1L, operation);
        System.out.println("是否签到:" + test.doesOperation(2L, operation));
        System.out.println("是否签到:" + test.doesOperation(1L, operation));
    }
}
