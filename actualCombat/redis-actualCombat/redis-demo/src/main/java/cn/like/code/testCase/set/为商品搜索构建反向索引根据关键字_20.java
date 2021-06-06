package cn.like.code.testCase.set;

import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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
 * desc
 *
 * @author like
 */
public class 为商品搜索构建反向索引根据关键字_20 {

    public void addProductInTagSet(String product, List<String> tags) {
        for (String tag : tags) {
            reactive().sadd("tag:" + tag + ":product", product).subscribe();
        }
    }

    public Mono<Set<String>> listProductByTag(List<String> tags) {
        String[] keys = new String[tags.size()];
        int i = 0;
        for (String tag : tags) {
            keys[i++] = "tag:" + tag + ":product";
        }
        return reactive().sinter(keys).collect(Collectors.toSet());
    }

    public static void main(String[] args) {
        为商品搜索构建反向索引根据关键字_20 test = new 为商品搜索构建反向索引根据关键字_20();
        List<String> set = new ArrayList<String>() {
            {
                add("1");
                add("2");
                add("3");
            }
        };
        test.addProductInTagSet("1", set);
        test.addProductInTagSet("2", set);
        test.addProductInTagSet("3", set);
        set.remove("2");
        test.addProductInTagSet("4", set);

        set.add("2");
        test.listProductByTag(set).subscribe(s -> {
            System.out.println(s);
        });

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
