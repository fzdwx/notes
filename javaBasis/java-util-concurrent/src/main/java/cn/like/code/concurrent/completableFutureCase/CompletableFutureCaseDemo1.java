package cn.like.code.concurrent.completableFutureCase;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * desc: 异步调用 <br>
 * details:
 *
 * @author like 980650920@qq.com
 * @date 2021-07-06 09:54:04
 */
public class CompletableFutureCaseDemo1 {
	
	public static void main(String[] args) {
		
		// 有返回值的 异步调用
		CompletableFuture<Integer> getInteger = CompletableFuture.supplyAsync(() -> {
			System.out.println(Thread.currentThread().getName());
			
			int i = 10 / 0;
			return 1;
		});
		
		getInteger.whenCompleteAsync((r, e) -> {
			
			System.out.println("r = " + r);
			
			System.out.println("==========");
			
			e.printStackTrace();
			
			System.out.println("e.getMessage() = " + e.getMessage());
		});
		
		// 等待异步调用完成
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
