package cn.like.code.web.lettuce.service;

import cn.like.code.web.lettuce.SyncCommandCallback;

/**
 * @author like
 * @date 2021/6/1 9:31
 * @desc redis 客户端
 */
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
}

