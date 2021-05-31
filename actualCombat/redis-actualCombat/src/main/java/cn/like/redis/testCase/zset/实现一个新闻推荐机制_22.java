package cn.like.redis.testCase.zset;


import cn.hutool.core.util.PageUtil;
import io.lettuce.core.Limit;
import io.lettuce.core.Range;
import io.lettuce.core.ScoredValue;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

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
public class 实现一个新闻推荐机制_22 {

    public Flux<ScoredValue<String>> searchNews(Integer minTimestamp, Integer maxTimestamp, int pageNo, int pageSize) {
        return reactive().zrevrangebyscoreWithScores("news",
                Range.create(minTimestamp, maxTimestamp),
                Limit.create(
                        PageUtil.getStart(pageNo, pageSize),
                        PageUtil.getEnd(pageNo, pageSize)));
    }

    public Mono<Long> addNew(String news, long timeStamp) {
        return reactive().zadd("news", timeStamp, news);
    }

    public static void main(String[] args) {
        实现一个新闻推荐机制_22 test = new 实现一个新闻推荐机制_22();

        for (int i = 0; i < 20; i++) {
            test.addNew(String.valueOf(i + 1), i + 1).subscribe();
        }

        test.searchNews(0, 20, 1, 10).subscribe(s->{
            System.out.println(s);
        });

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
