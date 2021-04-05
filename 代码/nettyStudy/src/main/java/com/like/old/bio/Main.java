package com.like.old.bio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * @author like
 * @date 2021-01-31 11:13
 * @contactMe 980650920@qq.com
 * @description 思路
 *         1.創建一個綫程池
 *         2.如果有客戶端鏈接，就取出一個綫程
 */
@Slf4j
public class Main {

    public static final Integer port = 6666;
    public static final Integer coolPoolSize = 10;
    public static final Integer maxPoolSize = 50;
    public static final Long keepAliveTime = 5L;
    // 1.創建一個綫程池
    public static final ExecutorService pool = new ThreadPoolExecutor(coolPoolSize, maxPoolSize, keepAliveTime, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(10000), Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy());
    private static ServerSocket server;

    public static void main(String[] args) throws IOException {

        // 2.創建serverSocket
        try {
            server = new ServerSocket(port);
        } catch (IOException e) {
            log.error("服务器创建失败：" + e.getCause());
        }
        log.info("服务器启动了:" + server.getInetAddress() + ":" + port);

        while (true) {
            log.info("等待连接···");
            // 3.监听是否有客户端连接
            Socket socket = server.accept();
            // 4. 创建一个线程与之通信
            pool.execute(() -> {
                handler(socket);
            });
        }
    }

    private static void handler(Socket socket) {
        log.info("连接到一个客户端：" + socket.getRemoteSocketAddress());
        byte[] clientData;
        try (InputStream is = socket.getInputStream()) {
            clientData = new byte[1024 * 1024];

            while (true) { // 循环读取客户端发送的数据
                int hasData = is.read(clientData);
                if (hasData != -1) {
                    String clientInfo = new String(clientData, 0, hasData);

                    System.out.println(clientInfo);  // 输出读取到的信息
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
