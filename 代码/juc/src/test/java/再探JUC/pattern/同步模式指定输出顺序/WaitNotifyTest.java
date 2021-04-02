package 再探JUC.pattern.同步模式指定输出顺序;

import java.text.SimpleDateFormat;
import java.util.Date;

class WaitNotifyTest {

    public static void main(String[] args) {
        Date d = new Date();

        SimpleDateFormat sdf = new SimpleDateFormat("HH-mm-ss");
        String format = sdf.format(d);

        System.out.println(d);
        System.out.println(format);
    }
//    public static void main(String[] args) {
//        Print print = new Print(1, 5);
//        new Thread(() -> {
//            print.print("a", 1, 2);
//        }, "a").start();
//        new Thread(() -> {
//            print.print("b", 2, 3);
//        }, "b").start();
//        new Thread(() -> {
//            print.print("c", 3, 1);
//        }, "c").start();
//        System.out.println("1");
//    }


}

class Print {
    private int flag;
    private int loop;

    public Print(int flag, int loop) {
        this.flag = flag;
        this.loop = loop;
    }

    public void print(String val, int curr, int next) {
        for (int i = 0; i < loop; i++) {
            synchronized (this) {
                while (flag != curr) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(val);
                flag = next;
                this.notifyAll();
            }
        }
    }
}