package cn.like.code.concurrent.threadInterruptCase;

import java.util.concurrent.TimeUnit;

/**
 * desc: 中断 <br>
 * details:
 *
 * @author like 980650920@qq.com
 * @date 2021-07-06 12:35:18
 */
public class InterruptCase1 {
	
	public static void main(String[] args) {
		final Thread t = new Thread(() -> {
			try {
				System.out.println("---线程启动");
				TimeUnit.SECONDS.sleep(2);
				System.out.println("---线程结束");
			} catch (Exception e) {
				System.out.println("---线程发生异常");
				e.printStackTrace();
			}
		});
		
		t.start();
		
		t.interrupt();
		
		System.out.println("主线程启动");
	}
}

