package com.like.netty.demo2;

import org.slf4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

import static com.like.netty.util.ByteBufferUtil.debugAll;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Create By like On 2021-04-08 20:26
 * @Description: 多线程下的nio  server
 */

public class MultiThreadServer {
    private final static Logger log = getLogger(MultiThreadServer.class);
    public static void main(String[] args) throws IOException {
        Thread.currentThread().setName("Boss");
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        Selector boss = Selector.open();
        SelectionKey bossKey = ssc.register(boss, 0, null);
        bossKey.interestOps(SelectionKey.OP_ACCEPT);
        ssc.bind(new InetSocketAddress(8888));

        ArrayList<Worker> workers = new ArrayList<>();
        workers.add(new Worker("worker-1"));
        workers.add(new Worker("worker-2"));

        while (true) {
            boss.select();
            Iterator<SelectionKey> iter = boss.selectedKeys().iterator();
            while (iter.hasNext()) {
                SelectionKey key = iter.next();
                iter.remove();
                if (key.isAcceptable()) {
                    SocketChannel sc = ssc.accept();
                    System.out.println(sc.getRemoteAddress());
                    sc.configureBlocking(false);
                    // 关联
                    int index = new Random().nextInt(2);
                    System.out.println(index);
                    workers.get(index).register(sc);
                }
            }
        }
    }

    /**
     * 工人
     * @Description: 专门检测读写事件
     */
    static class Worker implements Runnable {
        private Thread thread;
        private Selector worker;
        private String name;

        private volatile boolean start = false; // 还未初始化
        private ConcurrentLinkedQueue<Runnable> queue = new ConcurrentLinkedQueue<>();

        public Worker(String name) {
            this.name = name;
        }

        /**
         * 初始化线程 和selector
         * @param sc
         */
        public void register(SocketChannel sc) throws IOException {
            if (!start) {
                this.thread = new Thread(this, this.name);
                this.worker = Selector.open();
                this.thread.start();

                start = true;
            }
            // 向队列添加任务，不立即执行
            queue.add(() -> {
                try {
                    sc.register(this.worker, SelectionKey.OP_READ, null);
                } catch (ClosedChannelException e) {
                    e.printStackTrace();
                }
            });
            worker.wakeup(); // 唤醒 select 方法
        }

        @Override
        public void run() {
            while (true) {
                try {
                    log.info(Thread.currentThread().getName());

                    worker.select();
                    Runnable scRegisterTask = queue.poll();
                    if (!Objects.isNull(scRegisterTask)) {
                        scRegisterTask.run();  // 执行  sc.register(this.worker, SelectionKey.OP_READ, null);
                    }
                    Iterator<SelectionKey> iter = worker.selectedKeys().iterator();
                    while (iter.hasNext()) {
                        SelectionKey key = iter.next();
                        iter.remove();
                        if (key.isReadable()) {
                            ByteBuffer buffer = ByteBuffer.allocate(16);
                            SocketChannel sc = (SocketChannel) key.channel();
                            int read = sc.read(buffer);
                            if (read == -1) {
                                sc.close();
                            }
                            buffer.flip();
                            debugAll(buffer);
                        } else if (key.isWritable()) {
                            ByteBuffer buffer = ByteBuffer.wrap("this is worker".getBytes());
                            SocketChannel sc = (SocketChannel) key.channel();
                            sc.write(buffer);
                            buffer.flip();
                        }
                    }
                } catch (IOException e) {

                }
            }
        }
    }
}
