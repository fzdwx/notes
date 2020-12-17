package Java并发编程_JavaUtilConcurrent;

import java.util.concurrent.CountDownLatch;

/**
 * @author likeLove
 * @since 2020-09-24  16:20
 */
public class CountDownLatchDemo {
    public static void main(String[] args) {

        CountDownLatch count = new CountDownLatch(6);
        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t 国,被灭");
                count.countDown();
            },Country.get(i) ).start();
        }
        try {
            count.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "\t  秦国统一");
    }
}

enum Country {
    //国家枚举
    ONE(1, "齐"), TWO(2, "楚"), three(3, "燕"),four(4,"赵"),five(5,"魏"),six(6,"韩");
    public static String get(int index){
        Country[] arr = Country.values();
        return arr[index].s;
    }

    private int i;
    private String s;
    Country(int i, String s) {
        this.i = i;
        this.s = s;
    }

    public int getI() {
        return i;
    }

    public String getS() {
        return s;
    }
}
