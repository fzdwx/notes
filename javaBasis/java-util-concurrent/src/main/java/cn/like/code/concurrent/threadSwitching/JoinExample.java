package cn.like.code.concurrent.threadSwitching;

/**
 * desc: join 案例 <br>
 * details:
 *
 * @author like 980650920@qq.com
 * @date 2021-07-06 15:09:53
 */
public class JoinExample {
	
	private static class A extends Thread {
		@Override
		public void run() {
			System.out.println("A");
		}
	}
	
	private static class B extends Thread {
		
		private A a;
		
		B(A a) {
			this.a = a;
		}
		
		@Override
		public void run() {
			try {
				// 在当前线程调用另一个线程的join方法，让另一个线程运行，当前线程挂起
				// b 挂起，运行a，a运行完毕后在运行b
				a.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("B");
		}
	}
	
	public static void main(String[] args) {
		A a = new A();
		B b = new B(a);
		b.start();
		a.start();
	}
}
