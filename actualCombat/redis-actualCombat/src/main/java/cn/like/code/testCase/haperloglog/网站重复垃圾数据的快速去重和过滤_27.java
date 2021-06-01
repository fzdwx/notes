package cn.like.code.testCase.haperloglog;

import static cn.like.code.testCase.Redis.sync;

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
public class 网站重复垃圾数据的快速去重和过滤_27 {

    public boolean isGarbageInfo(String content) {
        return sync().pfadd("garbage_info", content) == 0;
    }

    public static void main(String[] args) {
        网站重复垃圾数据的快速去重和过滤_27 test = new 网站重复垃圾数据的快速去重和过滤_27();
        String content = "正常的内容";

        for (int i = 0; i < 10; i++) {
            System.out.println("是否是垃圾内容:"+ (test.isGarbageInfo(content)?"是垃圾":"不是垃圾"));
        }
    }
}
