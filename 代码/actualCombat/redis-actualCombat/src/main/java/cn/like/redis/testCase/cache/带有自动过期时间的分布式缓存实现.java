package cn.like.redis.testCase.cache;

import io.lettuce.core.SetArgs;

import static cn.like.redis.testCase.Redis.sync;

/**
 * @author: like
 * @since: 2021/5/19 19:58
 * @email: 980650920@qq.com
 * @desc:
 */
public class 带有自动过期时间的分布式缓存实现 {

    public void test() {
        // 分布式锁
        sync().set("lock", "1", SetArgs.Builder.ex(199).nx());
    }
}
