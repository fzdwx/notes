package cn.like.code.concurrent.newThreadMetheds;

/**
 * desc: 继承thread来创建一个线程 <br>
 *
 * @author like 980650920@qq.com
 * @date 2021-07-06 11:01:09
 */
public class ExtendsThreadCase {
	
	public static void main(String[] args) {
		final Thread t1 = new SayHello();
		
		// 运行
		t1.start();
	}
	
	private static class SayHello extends Thread {
		
		// 具体要执行的内容
		@Override
		public void run() {
			System.out.println("hello");
		}
	}
}
