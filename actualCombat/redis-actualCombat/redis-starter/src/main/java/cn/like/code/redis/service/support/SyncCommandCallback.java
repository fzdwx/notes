package cn.like.code.redis.service.support;

import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.cluster.api.sync.RedisAdvancedClusterCommands;
import io.lettuce.core.cluster.api.sync.RedisClusterCommands;

/**
 * @author like
 * @date 2021/6/1 9:29
 * @desc 抽象方法，为了简化代码，便于传入回调函数
 */
@FunctionalInterface
public interface SyncCommandCallback<T> {

    /**
     * 使用 RedisService#executeSync 传入的 commands 执行我们定义的命令
     * <pre>
     *      q1.为什么传入RedisClusterCommands?
     *          1.RedisCommands继承RedisClusterCommands
     *              {@link RedisCommands } 具有 400 多种方法的完整同步和线程安全的 Redis API。
     *          2.RedisAdvancedClusterCommands继承RedisClusterCommands
     *              {@link RedisAdvancedClusterCommands } 高级同步和线程安全的 Redis 集群 API。
     *       q2.使用demo
     *              final Long res = redisService.executeSync(commands -> {
     *                   final Boolean hSet = commands.hset("13", "123", "1");
     *                   Long del = 0L;
     *                   if (hSet) {
     *                       del = commands.del("13");
     *                   }
     *                   return del;
     *              });
     * </pre>
     *
     * @param commands 命令
     * @return {@link T}
     */
    T apply(RedisClusterCommands<String, String> commands);
}