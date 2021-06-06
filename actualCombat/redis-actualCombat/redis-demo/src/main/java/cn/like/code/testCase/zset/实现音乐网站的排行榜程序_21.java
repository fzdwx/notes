package cn.like.code.testCase.zset;

import io.lettuce.core.ScoredValue;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static cn.like.code.testCase.Redis.reactive;

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
public class 实现音乐网站的排行榜程序_21 {

    /**
     * 添加歌曲到指定排行榜中
     *
     * @param music     音乐
     * @param rankTopic 排名的话题
     * @return
     */
    public Mono<Long> addMusicInRankTopic(String music, String rankTopic) {
        return reactive().zadd("rankTopic:" + rankTopic + ":music", 0, music);
    }

    /**
     * 增加音乐的分数
     *
     * @param music     音乐
     * @param score     分数
     * @param rankTopic 排名的话题
     * @return
     */
    public Mono<Double> incrByMusicScore(String music, double score, String rankTopic) {
        return reactive().zincrby("rankTopic:" + rankTopic + ":music", score, music);
    }

    /**
     * 巨鲸音乐网排名话题
     *
     * @return
     */
    public Flux<List<ScoredValue<String>>> top100InRankTopic(String rankTopic) {
        return reactive().zrevrangeWithScores("rankTopic:" + rankTopic + ":music", 0, 100).buffer();
    }

    public static void main(String[] args) {
        实现音乐网站的排行榜程序_21 test = new 实现音乐网站的排行榜程序_21();
        String rankTopic = "Like";
        String m1 = "海浪 红白色乐队-四页景";
        String m2 = "Crawl Outta Love ILLENIUM / Annika Wells - Awake";
        String m3 = "Taking Me Higher ILLENIUM - Awake";
        String m4 = "极恶都市 夏日入侵企画 - 极恶都市";

        test.addMusicInRankTopic(m1, rankTopic).subscribe();
        test.addMusicInRankTopic(m2, rankTopic).subscribe();
        test.addMusicInRankTopic(m3, rankTopic).subscribe();
        test.addMusicInRankTopic(m4, rankTopic).subscribe();

        test.incrByMusicScore(m1, 1, rankTopic).subscribe();
        test.incrByMusicScore(m2, 8.1, rankTopic).subscribe();
        test.incrByMusicScore(m3, 8.8, rankTopic).subscribe();
        test.incrByMusicScore(m4, 5, rankTopic).subscribe();


        test.top100InRankTopic(rankTopic).subscribe(s -> {
            s.forEach(sv -> {
                System.out.println(sv.getValue() + " - " + sv.getScore());
            });
        });

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
