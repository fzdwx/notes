package com.like.nio.demo;



import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author like
 * @date 2021-01-31 15:10
 * @contactMe 980650920@qq.com
 * @description
 */
public class NIOFileChannelD1 {

    public static void main(String[] args) {
        write();
    }

    private static void write() {
        String s1 = "hello like";
        try {
            // 1.创建一个输出流
            FileOutputStream fos = new FileOutputStream("d:\\test.txt");
            // 2.根据流获取通道
            FileChannel fc = fos.getChannel();
            // 3.创建一个缓存区,并写入数据
            ByteBuffer buffer = ByteBuffer.allocate(s1.length());
            buffer.put(s1.getBytes());
            buffer.flip();
            // 4.将缓存区中的数据写入通道中
            fc.write(buffer);
            // 5.关闭流
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
