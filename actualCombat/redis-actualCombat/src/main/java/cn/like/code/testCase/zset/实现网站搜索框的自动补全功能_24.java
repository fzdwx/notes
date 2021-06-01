package cn.like.code.testCase.zset;

import cn.hutool.core.util.RandomUtil;
import reactor.core.publisher.Flux;

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
public class 实现网站搜索框的自动补全功能_24 {

    public void search(String keyword) {
        char[] chars = keyword.toCharArray();
        StringBuilder potentialKeyword = new StringBuilder("");
        for (char c : chars) {
            potentialKeyword.append(c);
            reactive().zincrby("potential_keyword:" + potentialKeyword + ":keywords", RandomUtil.randomDouble(0,1), keyword).subscribe();
        }
    }

    public Flux<List<String>> getAutoCompleteList(String potentialKeyword) {
        return reactive().zrevrange("potential_keyword:" + potentialKeyword + ":keywords", 0, 5).buffer();
    }

    public static void main(String[] args) {
        实现网站搜索框的自动补全功能_24 test = new 实现网站搜索框的自动补全功能_24();

        test.search("我爱李可");
        test.search("我爱中国");
        test.search("我很喜欢redis");
        test.search("我不喜欢看电视");
        test.search("我很喜欢学习spark");


        test.getAutoCompleteList("我").subscribe(s->{
            System.out.println("第一次自动补传:"+s);
        });

        test.getAutoCompleteList("我很").subscribe(s->{
            System.out.println("第二次自动补传:"+s);
        });

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
