package cn.like.redis.testCase.hash;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import io.lettuce.core.KeyValue;
import lombok.Data;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static cn.like.redis.testCase.Redis.reactive;
import static cn.like.redis.testCase.hash.对博客网站案例代码进行重构_09.BlogHelper.*;

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
public class 对博客网站案例代码进行重构_09 {
    public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public static void main(String[] args) {
        对博客网站案例代码进行重构_09 test = new 对博客网站案例代码进行重构_09();
        // 1.发布博客
        String id = UUID.randomUUID().toString();
        test.publishBlog(id, "我喜欢学习redis", "学习redis和lettuce很快乐", "like", new Date()).subscribe(res -> {
            for (int i = 0; i < 10; i++) {
                test.incrViewBlogCount(id).subscribe();
                test.likeBlog(id).subscribe();
            }

            // 2.查看博客
            test.getBlog(id).subscribe(list->{
                Blog blog = BeanUtil.mapToBean(list.stream().collect(Collectors.toMap(KeyValue::getKey, KeyValue::getValue)), Blog.class, false, null);
                System.out.println(blog);
            });

        });

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 发布博客
     *
     * @param title   标题
     * @param content 内容
     * @param author  作者
     * @param time    时间
     * @return boolean
     */
    public Mono<String> publishBlog(String id, String title, String content, String author, Date time) {
        Map<String, String> map = new HashMap<>(8);
        map.put(FIELD_ID, id);
        map.put(BlogHelper.FIELD_TITLE, title);
        map.put(BlogHelper.FIELD_CONTENT, content);
        map.put(BlogHelper.FIELD_AUTHOR, author);
        map.put(BlogHelper.FIELD_TIME, sdf.format(time));
        map.put(BlogHelper.FIELD_VIEW_COUNT, String.valueOf(0));
        map.put(BlogHelper.FIELD_LIKE_COUNT, String.valueOf(0));
        map.put(BlogHelper.FIELD_CONTENT_LENGTH, String.valueOf(content.length()));
        // reactor 设置blog信息后，获取博客内容长度

        reactive().incr( KEY_BLOG_COUNT).subscribe();
        return reactive().hmset(KEY_ARTICLE_PREFIX(id), map);
    }

    /**
     * 发布博客
     *
     * @param blog 博客
     * @return
     */
    public Mono<String> publishBlog(Blog blog) {
        return publishBlog(blog.getId(), blog.getTitle(), blog.getContent(), blog.getAuthor(), blog.getTime());
    }

    /**
     * 更新博客
     *
     * @param id      id
     * @param title   标题
     * @param content 内容
     * @param author  作者
     * @param time    时间
     * @return
     */
    public Mono<String> updateBlog(String id, String title, String content, String author, Date time) {
        HashMap<String, String> map = new HashMap<>(4);
        if (StrUtil.isNotBlank(title))
            map.put(FIELD_TITLE, title);
        if (StrUtil.isNotBlank(content)) {
            map.put(FIELD_CONTENT, content);
            map.put(FIELD_CONTENT_LENGTH, String.valueOf(content.length()));
        }
        if (StrUtil.isNotBlank(author))
            map.put(FIELD_AUTHOR, author);
        if (StrUtil.isNotBlank(time.toString()))
            map.put(FIELD_TIME, sdf.format(time));
        return reactive().hmset(KEY_ARTICLE_PREFIX(id), map);
    }

    /**
     * 更新博客
     *
     * @param blog 博客
     * @return
     */
    public Mono<String> updateBlog(Blog blog) {
        return updateBlog(blog.getId(), blog.getTitle(), blog.getContent(), blog.getAuthor(), blog.getTime());
    }

    /**
     * 博客
     *
     * @param id id
     * @return {@link Blog}
     */
    public Flux<List<KeyValue<String, String>>> getBlog(String id) {
        return reactive().hgetall(KEY_ARTICLE_PREFIX(id)).buffer();
    }

    /**
     * 点赞博客
     *
     * @param id id
     * @return {@link Mono<Long>}
     */
    public Mono<Long> likeBlog(String id) {
        return reactive().hincrby(KEY_ARTICLE_PREFIX(id), FIELD_LIKE_COUNT, BlogHelper.ADD_ONE);
    }

    /**
     * 取消 点赞博客
     *
     * @param id id
     * @return {@link Mono<Long>}
     */
    public Mono<Long> cancelLikeBlog(String id) {
        return reactive().hincrby(KEY_ARTICLE_PREFIX(id), FIELD_LIKE_COUNT, BlogHelper.SUB_ONE);
    }

    /**
     * 浏览 博客
     *
     * @param id id
     * @return {@link Mono<Long>}
     */
    public Mono<Long> incrViewBlogCount(String id) {
        return reactive().hincrby(KEY_ARTICLE_PREFIX(id), FIELD_VIEW_COUNT, ADD_ONE);
    }

    /**
     * 博客 助手
     *
     * @author pdd20
     */
    public static class BlogHelper {
        // ================ blog 存储在redis的key ================
        public static final String ARTICLE_PREFIX = "article:";
        public static final String KEY_BLOG_COUNT = "article:count";
        public static final String FIELD_ID = "ID";
        public static final String FIELD_TITLE = "title";
        public static final String FIELD_CONTENT = "content";
        public static final String FIELD_AUTHOR = "author";
        public static final String FIELD_TIME = "time";
        public static final String FIELD_CONTENT_LENGTH = "content_length";
        public static final String FIELD_VIEW_COUNT = "view_count";
        public static final String FIELD_LIKE_COUNT = "like_count";

        public static final long ADD_ONE = 1;
        public static final long SUB_ONE = -1;
        // ========================================================

        public static String KEY_ARTICLE_PREFIX(String id) {
            return ARTICLE_PREFIX + id;
        }

        /**
         * 获取 博客id
         *
         * @return {@link Mono<Long>}
         */
        public static Mono<Long> incrBlogId() {
            return reactive().incr(KEY_BLOG_COUNT);
        }
    }

    /**
     * 博客 实体类
     *
     * @author pdd20
     */
    @Data
    @NoArgsConstructor
    public static class Blog {
        private String id;
        private String title;
        private String content;
        private String author;
        private Date time;

        private long contentLength;
        private long likeCount;
        private long viewCount;

        public Blog(String id) {
            this.id = id;
        }

        public Blog(String id, String title, String content, String author, Date time) {
            this.id = id;
            this.title = title;
            this.content = content;
            this.author = author;
            this.time = time;
        }
    }
}
