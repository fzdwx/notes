package cn.like.code.redis.service;


import cn.hutool.json.JSONUtil;
import cn.like.code.redis.service.support.ASyncCommandCallback;
import cn.like.code.redis.service.support.ReactiveCommandCallback;
import cn.like.code.redis.service.support.SyncCommandCallback;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * desc: redis 操作类
 * details: 建议使用{@link cn.like.code.redis.support.AbstractRedisKey} 构建key
 *
 * @author like
 * @date 2021/6/1 9:31
 * @desc redis service
 */
public interface RedisService {

    /**
     * 子类实现该方法，决定是单机还是集群
     *
     * @param callback 回调 {@link SyncCommandCallback}
     * @return {@link T}
     */
    public <T> T sync(SyncCommandCallback<T> callback);

    public <T> T async(ASyncCommandCallback<T> callback);

    public <T> T reactive(ReactiveCommandCallback<T> callback);

    // ================ 全部用sync实现

    /** set key val */
    default String set(final String key, final String val) {
        return sync(commands -> commands.set(key, val));
    }

    /** get key */
    default String get(final String key) {
        return sync(commands -> commands.get(key));
    }

    /** get key  返回传入对象 */
    default <T> T get(String key, Class<T> clazz) {
        final String res = get(key);
        if (StringUtils.isBlank(res)) {
            return null;
        } else {
            return JSONUtil.toBean(res, clazz);
        }
    }

    /** setex  key val expire */
    default String setEX(String key, String val, Long expire) {
        return sync(cmd -> {
            return cmd.setex(key, expire, val);
        });
    }

    /** hset key filed value */
    default Long hset(String key, Map<String, String> map) {
        return sync(cmd -> {
            if (map.isEmpty()) return 0L;
            return cmd.hset(key, map);
        });
    }

    /** hset key filed value */
    default Boolean hest(String key, String filed, String value) {
        return sync(cmd -> {
            return cmd.hset(key, filed, value);
        });
    }

    /** hgetall key */
    default Map<String, String> hgetall(String key) {
        return sync(cmd -> {
            return cmd.hgetall(key);
        });
    }
}

