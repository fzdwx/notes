package 再探JUC.pattern.同步模式指定输出顺序;

import lombok.extern.slf4j.Slf4j;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-03-07 12:22
 */
@Slf4j
public class Test {
    public static void main(String[] args) {
        WaitNotify w = new WaitNotify(1, 5);
        new Thread(() -> {
            w.print("a",1, 2);
        }, "1").start();
        new Thread(() -> {
            w.print("b", 2, 3);
        }, "2").start();
        new Thread(() -> {
            w.print("c", 3, 1);
        }, "3").start();
    }
}

class WaitNotify {
    private int flag;
    private int loopNumber;

    public WaitNotify(int flag, int loopNumber) {
        this.flag = flag;
        this.loopNumber = loopNumber;
    }

    public void print(String value, int waitFlag, int nextFlag)  {
        for (int i = 0; i < loopNumber; i++) {
            synchronized (this) {
                while (flag != waitFlag) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print(value);
                flag = nextFlag;
                this.notifyAll();
            }
        }
    }
}