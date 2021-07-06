package cn.like.code.concurrent.completableFutureCase;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * desc: 异步调用 等待上一步完成后，在进行下一步操作 <br>
 * details: 使用 alLoF方法
 *
 * @author like 980650920@qq.com
 * @date 2021-07-06 10:49:37
 */
public class CompletableFutureCaseDemo2 {
	
	static String name = "like";
	static Integer age = 18;
	
	public static void main(String[] args) {
		final var demo = new CompletableFutureCaseDemo2();
		
		// 2个异步调用方法
		final var getStr = demo.getStr(name);
		final var getAge = demo.getAge(age);
		
		// 当2个都完成后才进行下一步操作
		CompletableFuture.allOf(getStr, getAge)
		  .whenCompleteAsync((v, t) -> {
			  try {
				  System.out.println(getStr.get());
				  
				  System.out.println(getAge.get());
				  
				  System.out.println(demo.helloWorld().get());
				  
			  } catch (InterruptedException | ExecutionException e) {
				  e.printStackTrace();
			  }
		  });
		
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public CompletableFuture<String> getStr(String name) {
		return CompletableFuture.supplyAsync(() -> {
			return "你好" + name + " !";
		});
	}
	
	public CompletableFuture<Integer> getAge(int age) {
		return CompletableFuture.supplyAsync(() -> {
			return age;
		});
	}
	
	public CompletableFuture<String> helloWorld() {
		return CompletableFuture.supplyAsync(() -> {
			return "hello world";
		});
	}
}
