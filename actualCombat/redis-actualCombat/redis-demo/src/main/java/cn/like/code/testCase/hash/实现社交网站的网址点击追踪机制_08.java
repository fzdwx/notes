package cn.like.code.testCase.hash;

import static cn.like.code.testCase.Redis.reactive;

/**
 * .____    .__ __
 * |    |   |__|  | __ ____
 * |    |   |  |  |/ // __ \
 * |    |___|  |    <\  ___/
 * |_______ \__|__|_ \\___  >
 * \/       \/    \/
 * <p>
 * 短链接追踪案例
 *
 * @author like
 * @date 2021-05-05 17:15
 */
public class 实现社交网站的网址点击追踪机制_08 {

    public static final String X36 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String[] X36_ARRAY = X36.split("");

    /** 根据当前seed生成短链接 越大越看不懂 */
    private static final String KEY_SHORT_URL_SEED = "SHORT_URL_SEED";
    /**
     * 短连接访问次数
     * SHORT_URL_ACCESS_COUNT:{
     *  https://like.com/97G78 : 156
     *  https://like.com/6gQDf : 334
     * }
     */
    private static final String KEY_SHORT_URL_ACCESS_COUNT = "SHORT_URL_ACCESS_COUNT";
    /**
     * 长短 url映射
     * URL_MAPPING:{
     *  https://like.com/97G78 :https://apppukyptrl1086.pc.xia123oe-tech.com
     * }
     */
    private static final String KEY_URL_MAPPING = "URL_MAPPING";
    /** 网络前缀 */
    private static final String WEB_PREFIX = "https://like.com/";

    public static void main(String[] args) {
        实现社交网站的网址点击追踪机制_08 test = new 实现社交网站的网址点击追踪机制_08();
        String shortUrl = test.getShortUrl("https://apppukyptrl1086.pc.xia123oe-tech.com");
        System.out.println("短链接为:" + shortUrl);

        for (int i = 0; i < 133; i++) {
            test.incrementShortURLAccessCount(shortUrl);
        }

        System.out.println("访问次数" + test.getShortURLAccessCount(shortUrl));

    }

    /**
     * 得到短url
     *
     * @param url url
     * @return {@link String}
     */
    public String getShortUrl(String url) {
        Long shortUrlSeed = reactive().incr(KEY_SHORT_URL_SEED).block();

        StringBuilder sb = new StringBuilder();

        if (shortUrlSeed == null || shortUrlSeed == 0) {
            shortUrlSeed = 0L;
            sb.append("0");
        }

        // 转换为36进制
        while (shortUrlSeed > 0) {
            sb.append(X36_ARRAY[(int) (shortUrlSeed % 36L)]);
            shortUrlSeed = shortUrlSeed / 36;
        }

        sb.reverse();
        String shortUrl = sb.insert(0, WEB_PREFIX).toString();

        reactive().hset(KEY_SHORT_URL_ACCESS_COUNT, shortUrl, "0").subscribe();
        reactive().hset(KEY_URL_MAPPING, shortUrl, url).subscribe();

        return shortUrl;
    }

    /**
     * 增加 shortUrl 的访问次数
     *
     * @param shortUrl 短网址
     */
    public void incrementShortURLAccessCount(String shortUrl) {
        reactive().hincrby(KEY_SHORT_URL_ACCESS_COUNT, shortUrl, 1).subscribe();
    }

    /**
     * 增加 shortUrl 的访问次数
     *
     * @param shortUrl 短网址
     */
    public Long getShortURLAccessCount(String shortUrl) {
        return Long.valueOf(reactive().hget(KEY_SHORT_URL_ACCESS_COUNT, shortUrl).block());
    }
}
