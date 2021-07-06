package cn.like.code.concurrent.threadSwitching;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author like 980650920@qq.com
 * @date 2021/7/6 15:17
 */
public class WaitNotifyExample {
	
	public synchronized void before() {
		System.out.println("before");
		
		// 唤醒所有挂起的线程
		notifyAll();
	}
	
	public synchronized void after() {
		try {
			// 挂起，释放锁
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// 等待其他线程唤醒
		System.out.println("after");
	}
	
	public static void main(String[] args) {
		final WaitNotifyExample example = new WaitNotifyExample();
		
		Executor executor = Executors.newCachedThreadPool();
		
		executor.execute(example::after);
		executor.execute(example::before);
	}
}
