package cn.like.code.testCase.list;

import cn.hutool.core.util.PageUtil;
import cn.hutool.core.util.StrUtil;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;
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
public class 实现OA系统中的待办事项列表管理_12 {

    public static String KEY_TODO_EVENT(String userId) {
        return "todoEvent:" + userId;
    }

    public static String KEY_COMPLETE_TODO_EVENT(String userId) {
        return "competeTodoEvent:" + userId;
    }

    /**
     * 添加todo事件
     *
     * @param userId    用户id
     * @param todoEvent todo事件
     * @return {@link Mono<Long>}
     */
    public Mono<Long> addTodoEvent(String userId, String todoEvent) {
        return reactive().rpush(KEY_TODO_EVENT(userId), todoEvent);
    }

    /**
     * todo事件 列表
     *
     * @param userId     用户id
     * @param pageSize   页面大小
     * @param pageNumber 页码
     * @return {@link Flux<List<String>>}
     */
    public Flux<List<String>> listTodoEvent(String userId, int pageNumber, int pageSize) {
        int start = PageUtil.getStart(pageNumber, pageSize);
        return reactive().lrange(KEY_TODO_EVENT(userId), start, start + pageSize).buffer();
    }

    /**
     * 插入todo事件
     *
     * @param userId    用户id
     * @param todoEvent todo事件
     * @param index     插入索引
     */
    public void insertTodoEvent(String userId, String todoEvent, int index) {
        String key = KEY_TODO_EVENT(userId);

        String pivot = reactive().lindex(key, index).block();
        if (StrUtil.isBlank(pivot)) return;

        reactive().linsert(key, true, pivot, todoEvent).subscribe();

    }

    /**
     * 完成任务的事件
     *
     * @param userId 用户id
     * @param index  指数
     */
    public void completeTodoEvent(String userId, int index) {
        String todoEvent = reactive().lindex(KEY_TODO_EVENT(userId), index).block();
        if (StrUtil.isBlank(todoEvent)) return;

        reactive().lrem(KEY_TODO_EVENT(userId), 1, todoEvent).subscribe(count -> {
            System.out.println("block = " + count);
            if (count > 0) {
                System.out.println("完成" + todoEvent);

                reactive().rpush(KEY_COMPLETE_TODO_EVENT(userId), todoEvent).subscribe();
            } ;
        });
    }

    /**
     * 查看完成的todo事件
     *
     * @param userId     用户id
     * @param pageNumber 页码
     * @param pageSize   页面大小
     * @return {@link Flux<List<String>>}
     */
    public Flux<List<String>> viewCompleteTodoEvent(String userId, int pageNumber, int pageSize) {
        int start = PageUtil.getStart(pageNumber, pageSize);
        return reactive().lrange(KEY_COMPLETE_TODO_EVENT(userId), start, start + pageSize).buffer();
    }

    public static void main(String[] args) {
        实现OA系统中的待办事项列表管理_12 test = new 实现OA系统中的待办事项列表管理_12();
        String userId = UUID.randomUUID().toString();
        for (int i = 0; i < 20; i++) {
            test.addTodoEvent(userId, String.format("第%s个待办事项", i + 1)).subscribe();
        }

        test.listTodoEvent(userId, 0, 20).subscribe(s -> {
            System.out.println("第一次");
            System.out.println(s);
        });

        int index = 3;
        test.completeTodoEvent(userId, index);

        test.listTodoEvent(userId, 0, 20).subscribe(s -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("第二次");
            System.out.println(s);
        });


        test.insertTodoEvent(userId, "插入到第3个位置的后面", index);

        test.listTodoEvent(userId, 0, 20).subscribe(s -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("第三次");
            System.out.println(s);
        });


        test.viewCompleteTodoEvent(userId, 0, 20).subscribe(s -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("查看完成的");
            System.out.println(s);
        });


        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
