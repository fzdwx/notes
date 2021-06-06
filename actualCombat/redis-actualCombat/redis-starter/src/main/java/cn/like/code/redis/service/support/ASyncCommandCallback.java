package cn.like.code.redis.service.support;

import io.lettuce.core.cluster.api.async.RedisClusterAsyncCommands;

/**
 * @see SyncCommandCallback
 * @author like
 * @date 2021/6/1 9:29
 * @desc 抽象方法，为了简化代码，便于传入回调函数
 */
@FunctionalInterface
public interface ASyncCommandCallback<T> {

    T apply(RedisClusterAsyncCommands<String, String> commands);
}