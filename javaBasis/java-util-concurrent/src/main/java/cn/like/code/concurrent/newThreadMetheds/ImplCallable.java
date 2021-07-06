package cn.like.code.concurrent.newThreadMetheds;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @author like 980650920@qq.com
 * @date 2021/7/6 11:05
 */
public class ImplCallable {
	
	public static void main(String[] args) throws ExecutionException, InterruptedException {
		// 使用futureTask包装一下我们的callable
		final FutureTask<Integer> task = new FutureTask<>(new GetInt());
		
		// 启动
		new Thread(task).start();
		
		System.out.println(task.get());
		
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static class GetInt implements Callable<Integer> {
		
		// callable 是一个带有返回值的
		@Override
		public Integer call() throws Exception {
			return 1;
		}
	}
}
