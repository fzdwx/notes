package cn.like.code.testCase.set;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static cn.like.code.testCase.Redis.reactive;

/**
 * .____    .__ __
 * |    |   |__|  | __ ____
 * |    |   |  |  |/ // __ \
 * |    |___|  |    <\  ___/
 * |_______ \__|__|_ \\___  >
 * \/       \/    \/
 *
 * 此时你的好友可能会
 * 点赞，
 * 取消点赞,
 * 好友也可以查看是否对这个朋友圈点赞。
 * 你也可以查看这条朋友圈有多少人点赞，
 * 哪些人点赞
 *
 * @author like
 */
public class 朋友圈点赞功能的实现_16 {

    /**
     * 点赞
     *
     * @param user     用户
     * @param momentId 时刻id
     * @return {@link Mono<Long>}
     */
    public Mono<Long> like(String user, String momentId) {
        return reactive().sadd("moment_like_user_set:" + momentId, user);
    }

    /**
     * 取消点赞
     *
     * @param user     用户
     * @param momentId 时刻id
     * @return {@link Mono<Long>}
     */
    public Mono<Long> disLike(String user, String momentId) {
        return reactive().srem("moment_like_user_set:" + momentId, user);
    }

    /**
     * 是否点赞
     *
     * @param user     用户
     * @param momentId 时刻id
     * @return {@link Mono<Boolean>}
     */
    public Mono<Boolean> hasLike(String user, String momentId) {
        return reactive().sismember(user, momentId);
    }

    /**
     * 点赞的用户
     *
     * @param momentId 时刻id
     * @return {@link Flux<String>}
     */
    public Flux<String> likeUsers(String momentId) {
        return reactive().smembers("moment_like_user_set:" + momentId);
    }

    /**
     * 点赞的数量
     *
     * @param momentId 时刻id
     * @return {@link Mono<Long>}
     */
    public Mono<Long> likeCount(String momentId) {
        return reactive().scard("moment_like_user_set:" + momentId);
    }

    public static void main(String[] args) {
        朋友圈点赞功能的实现_16 test = new 朋友圈点赞功能的实现_16();
        List<String> likeUsers;
        // 我的朋友
        String u1 = "小明";
        String u2 = "小可";
        String u3 = "小雷";
        String u4 = "小新";

        // 我发了一条朋友圈
        String momentId = "1";

        // u1 u2 u3 u4 对我的朋友圈点赞了
        test.like(u1,momentId).subscribe();
        test.like(u2,momentId).subscribe();
        test.like(u3,momentId).subscribe();
        test.like(u4, momentId).subscribe();

        likeUsers = test.likeUsers(momentId).collect(Collectors.toList()).block();
        System.out.println("第一次："+likeUsers);

        // u3取消点赞
        test.disLike(u3, momentId).subscribe();
        likeUsers = test.likeUsers(momentId).collect(Collectors.toList()).block();
        System.out.println("第二次："+likeUsers);
        // 查看是否点赞了
        test.hasLike(u3, momentId).subscribe(s->{
            System.out.println(s);
        });


        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
