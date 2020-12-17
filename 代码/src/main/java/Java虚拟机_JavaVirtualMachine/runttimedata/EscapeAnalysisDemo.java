package Java虚拟机_JavaVirtualMachine.runttimedata;

import java.util.concurrent.TimeUnit;

/**
 * @author likeLove
 * @since 2020-10-06  10:38
 */
public class EscapeAnalysisDemo {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        for (int i = 1; i <= 1000000; i++) {
            EscapeAnalysisDemo escapeAnalysisDemo = new EscapeAnalysisDemo();
        }
        long end = System.currentTimeMillis();

        System.out.println(end - start);


        try {TimeUnit.SECONDS.sleep(10000); } catch (InterruptedException e) {e.printStackTrace();}
    }
}
