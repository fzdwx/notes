package com.like.old.nio.demo;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author like
 * @date 2021-01-31 15:32
 * @contactMe 980650920@qq.com
 * @description
 */
public class NIOFileChannelD3 {
    public static void main(String[] args) throws IOException {
        File f = new File("d:\\test.txt");

        FileInputStream fis = new FileInputStream(f);
        FileChannel fic = fis.getChannel();

        FileOutputStream fos = new FileOutputStream("test.txt");
        FileChannel foc = fos.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(Math.toIntExact(f.length()));


        while (true) {
            buffer.clear(); // 清空buffer

            int len = fic.read(buffer);
            if (len == -1) {
                break;
            }
            buffer.flip();
            foc.write(buffer);
        }

        fis.close();
        fos.close();

    }
}
