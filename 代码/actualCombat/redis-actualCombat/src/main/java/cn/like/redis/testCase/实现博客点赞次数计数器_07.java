package cn.like.redis.testCase;

import static cn.like.redis.testCase.Redis.cmd;

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
 * @date 2021-05-05 17:11
 */
public class 实现博客点赞次数计数器_07 {

    public static void main(String[] args) {
        cmd().incr("article:1:goodCount");
    }
}
