package cn.like.code.testCase;

import io.lettuce.core.RedisURI;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.reactive.RedisAdvancedClusterReactiveCommands;

import java.util.HashSet;
import java.util.Set;

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
public class RedisCluster {
    public static void main(String[] args) {
        test();

    }

    public static void test() {
        Set<RedisURI> redisURIS = new HashSet<>(16);
        redisURIS.add(RedisURI.builder()
                .withHost("192.168.1.11")
                .withPort(7001)
                .build());
       /* redisURIS.add(RedisURI.builder()
                .withHost("192.168.1.11")
                .withPort(7002)
                .build());
        redisURIS.add(RedisURI.builder()
                .withHost("192.168.1.12")
                .withPort(7003)
                .build());
        redisURIS.add(RedisURI.builder()
                .withHost("192.168.1.12")
                .withPort(7004)
                .build());
        redisURIS.add(RedisURI.builder()
                .withHost("192.168.1.13")
                .withPort(7005)
                .build());
        redisURIS.add(RedisURI.builder()
                .withHost("192.168.1.13")
                .withPort(7006)
                .build());*/
        RedisClusterClient client = RedisClusterClient.create(redisURIS);
        RedisAdvancedClusterReactiveCommands<String, String> redis = client.connect().reactive();
        while (true) {
            if (redis.isOpen()) {
                redis.set("hello", "world").block();
                System.out.println(redis.get("hello").block());
                System.out.println(redis.clusterNodes().block());
                System.out.println(redis.clusterInfo().block());
                break;
            }
        }

    }
}
