package cn.like.redis.testCase.zset;

import io.lettuce.core.ScoredValue;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
public class 大数据商品关系推荐机制_23 {


    /**
     * 继续购买
     *
     * @param product     产品
     * @param nextProduct 下一个产品
     * @return {@link Mono<Double>}
     */
    public Mono<Double> continueToBuy(String product, String nextProduct) {
        return reactive().zincrby("product:" + product + "continueToBuy", 1, nextProduct);
    }

    /**
     * 推荐
     *
     * @param product 产品
     * @return {@link Flux<ScoredValue<String>>}
     */
    public Flux<ScoredValue<String>> recommended(String product) {
        return reactive().zrevrangeWithScores("product:" + product + "continueToBuy", 0, 3);
    }
}
