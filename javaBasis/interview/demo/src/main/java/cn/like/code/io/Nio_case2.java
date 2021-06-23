package cn.like.code.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.*;

/**
 * desc: nio模型
 * details: 引入nio中的多路复用器 select
 *
 * @author like 980650920@qq.com
 * @since 2021/6/23 21:22
 */
public class Nio_case2 {
	
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
			Selector selector = Selector.open();
			
			// 把server 注册到 多路复用器上去 监听连接事件
			ssc.register(selector, SelectionKey.OP_ACCEPT);
			
			while (true) {
				// 阻塞等待，需要监听的事件发生
				selector.select();
				// 获取 selector 中注册的全部事件 (这个keys 就是 收集所有发生了事件的channel ，比如serverSocketChannel 以及 socketChannel)
				Set<SelectionKey> keys = selector.selectedKeys();
				
				// 进行遍历
				Iterator<SelectionKey> keyIter = keys.iterator();
				while (keyIter.hasNext()) {
					SelectionKey key = keyIter.next();
					keyIter.remove(); // remove
					
					if (key.isAcceptable()) { // OP_ACCEPT  处理连接请求
						var server = ((ServerSocketChannel) key.channel());
						client = server.accept();
						if (client != null) {
							client.configureBlocking(BLOCK);
							
							// 把client 注册到 多路复用器上去 监听可读事件
							client.register(selector, SelectionKey.OP_READ);
						}
						
					} else if (key.isReadable()) {  // OP_READ 处理读取时间
						buffer = ByteBuffer.allocate(128);
						
						client = (SocketChannel) key.channel();
						read = client.read(buffer);
						if (read > 0) {
							System.out.println("收到客户端消息: " + new String(buffer.array(), 0, read));
						} else if (read == -1) {
							client.close();
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
