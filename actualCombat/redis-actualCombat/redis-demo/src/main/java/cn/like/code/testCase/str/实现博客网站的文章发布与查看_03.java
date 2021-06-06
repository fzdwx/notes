package cn.like.code.testCase.str;

import io.lettuce.core.KeyValue;
import org.slf4j.Logger;
import reactor.core.publisher.Flux;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static cn.like.code.testCase.Redis.reactive;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * .____    .__ __
 * |    |   |__|  | __ ____
 * |    |   |  |  |/ // __ \
 * |    |___|  |    <\  ___/
 * |_______ \__|__|_ \\___  >
 * \/       \/    \/
 * <p>
 * desc
 *
 * @author like
 * @date 2021-05-05 16:11
 */
public class 实现博客网站的文章发布与查看_03 {
    private final static Logger log = getLogger(实现博客网站的文章发布与查看_03.class);

    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

        // 存
        HashMap<String, String> map = new HashMap<>(16);
        map.put("article:1:title", "学习使用redis");
        map.put("article:1:content", "实现博客网站的文章发布与查看");
        map.put("article:1:author", "like");
        map.put("article:1:time", sdf.format(new Date()));

        String res = reactive().mset(map).block();

        System.out.println(res);

        // 取
        Flux<KeyValue<String, String>> flux = reactive().mget("article:1:title");
        KeyValue<String, String> s = flux.blockLast();
        System.out.println(s);

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
