package cn.like.redis.testCase.hash;

import cn.hutool.core.util.StrUtil;
import io.lettuce.core.KeyValue;
import lombok.Data;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Mono;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static cn.like.redis.testCase.Redis.cmd;
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
        test.publishBlog("我喜欢学习redis", "学习redis和lettuce很快乐", "like", new Date());
        // 2.更新博客
        Blog blog = test.getBlog(1);

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(blog);

        blog.setContent("更新后的" + "学习redis和lettuce很快乐");
        test.updateBlog(blog);

        // 3.预览内容
        String s = test.previewBlog(blog.id).block();
        System.out.println("预览内容 = " + s);

        // 4.点赞
        test.likeBlog(blog.id);
        test.viewBlog(blog.id);


    }

    /**
     * 发布博客
     *
     * @param title   标题
     * @param content 内容
     * @param author  作者
     * @param time    时间
     * @return
     */
    public void publishBlog(String title, String content, String author, Date time) {
        publishBlog(new Blog(title, content, author, time));
    }

    /**
     * 发布博客
     *
     * @param blog 博客
     * @return
     */
    public void publishBlog(Blog blog) {
        // reactor 获取id后，设置blog信息
        incrBlogId().subscribe(id -> {
            blog.setId(id);
            HashMap<String, String> map = new HashMap<>(4);
            map.put(keyTitle(blog.getId()), blog.getTitle());
            map.put(keyContent(blog.getId()), blog.getContent());
            map.put(keyAuthor(blog.getId()), blog.getAuthor());
            map.put(keyTime(blog.getId()), sdf.format(blog.getTime()));
            map.put(keyViewCount(blog.getId()), String.valueOf(0));
            map.put(keyLikeCount(blog.getId()), String.valueOf(0));
            map.put(keyContentLength(blog.getId()), String.valueOf(0));
            // reactor 设置blog信息后，获取博客内容长度
            cmd().msetnx(map).subscribe(res -> {
                // reactor 获取博客内容长度后，设置博客内容长度
                cmd().strlen(keyContent(blog.getId())).subscribe(len -> {
                    // 设置博客内容长度
                    cmd().setnx(keyContentLength(blog.getId()), String.valueOf(len)).subscribe();
                });
            });
        });

    }

    /**
     * 更新博客
     *
     * @param id      id
     * @param title   标题
     * @param content 内容
     * @param author  作者
     * @param time    时间
     */
    public void updateBlog(long id, String title, String content, String author, Date time) {
        updateBlog(new Blog(id, title, content, author, time));
    }

    /**
     * 更新博客
     *
     * @param blog 博客
     */
    public void updateBlog(Blog blog) {
        HashMap<String, String> map = new HashMap<>(4);
        if (StrUtil.isNotBlank(blog.getTitle()))
            map.put(keyTitle(blog.getId()), blog.getTitle());
        if (StrUtil.isNotBlank(blog.getContent()))
            map.put(keyContent(blog.getId()), blog.getContent());
        if (StrUtil.isNotBlank(blog.getAuthor()))
            map.put(keyAuthor(blog.getId()), blog.getAuthor());
        if (StrUtil.isNotBlank(blog.getTime().toString()))
            map.put(keyTime(blog.getId()), sdf.format(blog.getTime()));
        // reactor 更新 blog信息后，获取博客内容长度
        cmd().mset(map).subscribe(s -> {
            // reactor 获取博客内容长度后，设置博客内容长度
            cmd().strlen(keyContent(blog.getId())).subscribe(len -> {
                // 设置博客内容长度
                cmd().setnx(keyContentLength(blog.getId()), String.valueOf(len)).subscribe();
            });
        });

    }

    /**
     * 博客
     *
     * @param id id
     * @return {@link Blog}
     */
    public Blog getBlog(long id) {
        Blog blog = new Blog(id);
        List<String> blogInfos = new ArrayList<>(cmd().mget(keyTitle(id), keyContent(id), keyAuthor(id), keyTime(id), keyContentLength(id), keyViewCount(id), keyLikeCount(id))
                .toStream().collect(Collectors.toList())
                .stream().collect(Collectors.toMap(KeyValue::getKey, KeyValue::getValue))
                .values());
        try {
            blog.setTitle(blogInfos.get(0));
            blog.setContent(blogInfos.get(1));
            blog.setAuthor(blogInfos.get(2));
            blog.setTime(sdf.parse(blogInfos.get(3)));
            blog.setContentLength(Long.parseLong(blogInfos.get(4)));
            blog.setViewCount(Long.parseLong(blogInfos.get(5)));
            blog.setLikeCount(Long.parseLong(blogInfos.get(6)));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println("blog = " + blog);
        return blog;
    }

    /**
     * 预览博客
     *
     * @param id id
     * @return {@link String}
     */
    public Mono<String> previewBlog(long id) {
        return cmd().getrange(keyContent(id), 0, 200);
    }

    /**
     * 点赞博客
     *
     * @param id id
     * @return {@link Mono<Long>}
     */
    public Mono<Long> likeBlog(long id) {
        return cmd().incr(keyLikeCount(id));
    }

    /**
     * 取消 点赞博客
     *
     * @param id id
     * @return {@link Mono<Long>}
     */
    public Mono<Long> cancelLikeBlog(long id) {
        return cmd().incr(keyLikeCount(id));
    }

    /**
     * 点赞 博客
     *
     * @param id id
     * @return {@link Mono<Long>}
     */
    public Mono<Long> viewBlog(long id) {
        return cmd().incr(keyViewCount(id));
    }

    /**
     * 博客 助手
     *
     * @author pdd20
     */
    public static class BlogHelper {
        // ================ blog 存储在redis的key ================
        public static final String KEY_BLOG_ID_COUNT = "article:id_count";

        public static String keyViewCount(long id) {
            return "article:" + id + ":view_count";
        }

        public static String keyLikeCount(long id) {
            return "article:" + id + ":like_count";
        }

        public static String keyContentLength(long id) {
            return "article:" + id + ":content_length";
        }

        public static String keyTime(long id) {
            return "article:" + id + ":time";
        }

        public static String keyAuthor(long id) {
            return "article:" + id + ":author";
        }

        public static String keyContent(long id) {
            return "article:" + id + ":content";
        }

        public static String keyTitle(long id) {
            return "article:" + id + ":title";
        }
        // ========================================================

        /**
         * 获取 博客id
         *
         * @return {@link Mono<Long>}
         */
        public static Mono<Long> incrBlogId() {
            return cmd().incr(KEY_BLOG_ID_COUNT);
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
        private long id;
        private String title;
        private String content;
        private String author;
        private Date time;

        private long contentLength;
        private long likeCount;
        private long viewCount;

        public Blog(long id) {
            this.id = id;
        }

        public Blog(long id, String title, String content, String author, Date time) {
            this.id = id;
            this.title = title;
            this.content = content;
            this.author = author;
            this.time = time;
        }

        public Blog(String title, String content, String author, Date time) {
            this.title = title;
            this.content = content;
            this.author = author;
            this.time = time;
        }
    }
}
