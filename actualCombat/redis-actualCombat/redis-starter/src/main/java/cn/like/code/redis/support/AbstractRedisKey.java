package cn.like.code.redis.support;

import java.text.MessageFormat;

/**
 * redis key 生成工具类
 * <pre>
 *     不带参数
 *     TEST_STRING = "test:string";
 *     带一个参数
 *     HELLO_WORLD = "hello:world:{0}";
 *     多个参数的key拼接
 *     TEST_STRING_ID_NAME="test:string:id:{0}:{1}",
 * </pre>
 * desc: 推荐使用方法,继承该类，然后以各种需求为命名+RedisKey,比如缓存类的需求: CacheKey
 *
 * @author like
 * @date 2021/06/04
 */
public class AbstractRedisKey {
    
    private String value;

    /**
     * value
     *
     * @param value value
     * @return {@link AbstractRedisKey}
     */
    public static AbstractRedisKey value(String value) {
        return new AbstractRedisKey().setValue(value);
    }

    /**
     * 构建Key
     *
     * @param params 参数
     * @return {@link String}
     */
    public final String buildKey(Object... params) {
        return MessageFormat.format(value, params);
    }

    private AbstractRedisKey setValue(String value) {
        this.value = value;
        return this;
    }
}
