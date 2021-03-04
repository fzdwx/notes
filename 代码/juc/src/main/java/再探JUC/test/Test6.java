package 再探JUC.test;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-03-04 11:31
 */
public class Test6 {
    static int i = 0;


    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (int x = 0; x < 5000; x++) {
                synchronized (Test6.class) {
                    i++;
                }
            }
        }, "1");
        Thread t2 = new Thread(() -> {
            for (int x = 0; x < 5000; x++) {
                synchronized (Test6.class) {
                    i--;
                }
            }
        }, "2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(i);
    }
}
