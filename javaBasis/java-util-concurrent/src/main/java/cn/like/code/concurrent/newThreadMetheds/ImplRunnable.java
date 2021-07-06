package cn.like.code.concurrent.newThreadMetheds;

/**
 * desc: 实现runnable 接口 <br>
 * details:
 *
 * @author like 980650920@qq.com
 * @date 2021-07-06 11:04:43
 */
public class ImplRunnable {
	
	public static void main(String[] args) {
		final Thread t1 = new Thread(new SayHello());
		
		// 运行
		t1.start();
	}
	
	private static class SayHello implements Runnable {
		
		// 具体要执行的内容
		@Override
		public void run() {
			System.out.println("hello");
		}
	}
}
