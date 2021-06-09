package cn.like.code.concurrent.case_1;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;

/**
 * desc: 栈封闭
 *
 * @author like
 * @date 2021/6/9 14:45
 */
@Slf4j
public class StackClosedExample {

    public void add() {
        int count = 0;
        for (int i = 0; i < 100; i++) {
            count++;
        }

        log.info("[add][ count ]: {}",count);
    }

    public static void main(String[] args) {
        final var example = new StackClosedExample();
        final var service = Executors.newCachedThreadPool();

        service.execute(example::add);
        service.execute(example::add);

        service.shutdown();
    }
}
