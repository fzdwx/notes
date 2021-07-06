package cn.like.code.concurrent.threadInterruptCase;

import java.util.concurrent.TimeUnit;

/**
 * desc: 判断当前线程是否被打断
 * <br>
 * details:
 *
 * @author like 980650920@qq.com
 * @date 2021-07-06 12:41:49
 */
public class InterruptCase2 {
	
	private static class Test extends Thread {
		@Override
		public void run() {
			// 判断当前线程是否被打断了
			while (!interrupted()) {
				// ..
				System.out.println(1);
			}
			System.out.println("Thread end");
		}
	}
	
	public static void main(String[] args) {
		final Test t = new Test();
		
		t.start();
		
		try {
			TimeUnit.MICROSECONDS.sleep(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		t.interrupt();
	}
}
