package cn.like.redis.testCase.set;

import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static cn.like.redis.testCase.Redis.reactive;

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
public class 实现类似微博的社交关系_18 {

    /**
     * 订阅
     *
     * @param beSubscribedTo 被订阅
     * @param user           用户
     */
    public void subscribe(String beSubscribedTo, String user) {
        reactive().sadd("user:" + user + ":stars", beSubscribedTo).subscribe();
        reactive().sadd("user:" + beSubscribedTo + ":fans", user).subscribe();
    }

    /**
     * 取消订阅
     *
     * @param beSubscribedTo 被订阅
     * @param user           用户
     */
    public void unSubscribe(String beSubscribedTo, String user) {
        reactive().srem("user::" + user + "::stars", beSubscribedTo).subscribe();
        reactive().srem("user::" + beSubscribedTo + "::fans", user).subscribe();
    }

    /**
     * 查看关注的用户
     *
     * @param user 用户
     * @return {@link Mono<Set<String>>}
     */
    public Mono<Set<String>> stars(String user) {
        return reactive().smembers("user:" + user + ":stars").collect(Collectors.toSet());
    }

    /**
     * 查看关注用户的数
     *
     * @param user 用户
     * @return {@link Mono<Long>}
     */
    public Mono<Long> starsCount(String user) {
        return reactive().scard("user:" + user + ":stars");
    }


    /**
     * 查看粉丝
     *
     * @param user 用户
     * @return {@link Mono<Set<String>>}
     */
    public Mono<Set<String>> fans(String user) {
        return reactive().smembers("user:" + user + ":fans").collect(Collectors.toSet());
    }

    /**
     * 查看粉丝数
     *
     * @param user 用户
     * @return {@link Mono<Long>}
     */
    public Mono<Long> fansCount(String user) {
        return reactive().scard("user:" + user + ":fans");
    }

    public static void main(String[] args) {
        String u1 = "小q";
        String u2 = "小w";
        String u3 = "小z";
        String u4 = "我";

        实现类似微博的社交关系_18 test = new 实现类似微博的社交关系_18();

        // 2、3、4 都关注 1
        test.subscribe(u1, u2);
        test.subscribe(u1, u3);
        test.subscribe(u1, u4);

        // 2、3 关注我
        test.subscribe(u4, u3);
        test.subscribe(u4, u2);
        test.subscribe(u4, u1);

        test.stars(u4).subscribe(s -> {
            System.out.println(u4 + "关注了：" + s);
        });

        test.fans(u4).subscribe(s -> {
            System.out.println(u4 + "的粉丝有：" + s);
        });

        test.fans(u1).subscribe(s -> {
            System.out.println(u1 + "的粉丝有：" + s);
        });


        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
