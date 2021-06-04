package cn.like.code.redis.service;


import cn.hutool.json.JSONUtil;
import cn.like.code.redis.SyncCommandCallback;
import org.apache.commons.lang3.StringUtils;

/**
 * @author like
 * @date 2021/6/1 9:31
 * @desc redis 客户端
 */
@SuppressWarnings("all")
public interface RedisService {

    /**
     * 子类实现该方法，决定是单机还是集群
     *
     * @param callback 回调 {@link SyncCommandCallback}
     * @return {@link T}
     */
    public <T> T executeSync(SyncCommandCallback<T> callback);

    /** set key val */
    default String set(final String key, final String val) {
        return executeSync(commands -> commands.set(key, val));
    }

    /** get key */
    default String get(final String key) {
        return executeSync(commands -> commands.get(key));
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
        return executeSync(cmd -> {
            return cmd.setex(key, expire, val);
        });
    }
}

