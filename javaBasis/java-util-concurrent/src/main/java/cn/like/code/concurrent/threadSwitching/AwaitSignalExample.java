package cn.like.code.concurrent.threadSwitching;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author like 980650920@qq.com
 * @date 2021/7/6 15:23
 */
public class AwaitSignalExample {
	
	private Lock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();
	
	public void before() {
		lock.lock();
		try {
			System.out.println("before");
			condition.signalAll();
		} finally {
			lock.unlock();
		}
	}
	
	public void after() {
		lock.lock();
		try {
			// 线程等待
			condition.await();
			// 被唤醒后继续执行
			System.out.println("after");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
	
	public static void main(String[] args) {
		ExecutorService executorService = Executors.newCachedThreadPool();
		
		AwaitSignalExample example = new AwaitSignalExample();
		executorService.execute(example::after);
		executorService.execute(example::before);
	}
}
