package cn.like.code.testCase.str;

import static cn.like.code.testCase.Redis.reactive;

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
 * @date 2021-05-05 17:06
 */
public class 实现一个简单的唯一ID生成器_06 {

    public static void main(String[] args) {
        reactive().incr("test");
    }
}
