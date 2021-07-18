package cn.like.code.concurrent.sync.reenter;

/**
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2021/7/18 19:33
 */
public class ReEnterLock implements Runnable {

    @Override
    public void run() {
        synchronized (this) {
            System.out.println("同步代码");
            method();
        }
    }

    private synchronized void method() {
        System.out.println("同步方法");
    }

    public static void main(String[] args) {
        new Thread(new ReEnterLock()).start();
    }
}
