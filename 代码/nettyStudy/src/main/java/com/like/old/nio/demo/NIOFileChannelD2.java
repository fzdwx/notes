package com.like.old.nio.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author like
 * @date 2021-01-31 15:23
 * @contactMe 980650920@qq.com
 * @description
 */
public class NIOFileChannelD2 {

    public static void main(String[] args) {
        try {
            File f = new File("d:\\test.txt");
            FileInputStream fis = new FileInputStream(f);
            FileChannel fc = fis.getChannel();

            ByteBuffer buffer = ByteBuffer.allocate(Math.toIntExact(f.length()));
            fc.read(buffer);

            System.out.println(new String(buffer.array()));

            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
