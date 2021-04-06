package com.like.netty.c1;

import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * Create By Like On 2021-04-06 19:30
 */
public class TestFileChannelTransferTo {

    @Test
    void testT1() {
        try (
                FileChannel read = new FileInputStream("D:\\github\\notes\\代码\\data.txt").getChannel();
                FileChannel write = new FileOutputStream("D:\\github\\notes\\代码\\to.txt").getChannel()
        ) {
            // size ： read文件的大小  left : 还剩数据的大小
            long size = read.size();
            for (long left = size; left > 0;) {
                // 底层是零拷贝，效率高，最大2g数据
                left -= read.transferTo(size - left, left, write);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
