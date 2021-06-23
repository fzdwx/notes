package cn.like.code.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * desc: nio模型
 * details: 使用channel以及buffer 简单使用一个集合来存放list,不断的轮询
 *
 * @author like 980650920@qq.com
 * @since 2021/6/23 21:22
 */
public class Nio_case1 {
	
	public static final int PORT = 8888;
	public static final boolean BLOCK = false;
	
	public static void main(String[] args) {
		List<SocketChannel> clients = new LinkedList<>();
		Iterator<SocketChannel> iter;
		SocketChannel client;
		int read;
		ByteBuffer buffer;
		
		// 启动服务端程序，绑定到8888端口
		try (ServerSocketChannel ssc = ServerSocketChannel.open().bind(new InetSocketAddress(PORT))) {
			ssc.configureBlocking(BLOCK); // 设置为非阻塞
			
			while (true) {
				// 接收客户端，不为null就存入到集合中。
				client = ssc.accept();
				if (!Objects.isNull(client)) {
					client.configureBlocking(BLOCK);  // 设置为非阻塞
					clients.add(client);
				}
				
				// 轮询客户端，查看是否有客户端发送消息。
				iter = clients.iterator();
				while (iter.hasNext()) {
					buffer = ByteBuffer.allocate(128);
					
					client = iter.next();
					read = client.read(buffer);
					if (read > 0) {
						System.out.println("收到客户端消息: " + new String(buffer.array(), 0, read));
					} else if (read == -1) {
						client.close();
						iter.remove();
					}
					
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
