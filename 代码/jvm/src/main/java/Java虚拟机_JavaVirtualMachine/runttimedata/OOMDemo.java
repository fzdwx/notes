package Java虚拟机_JavaVirtualMachine.runttimedata;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * @author likeLove
 * @since 2020-10-03  16:52
 */
public class OOMDemo {
    public static void main(String[] args) {
        System.out.println("hello");
        try {TimeUnit.SECONDS.sleep(1000); } catch (InterruptedException e) {e.printStackTrace();}
    }
    //-Xms50m -Xmx50m
    // 堆内存溢出
    private static void oomJavaHeapSpace() {
        ArrayList<int[]> ints = new ArrayList<>();
        while (true) {
            try {TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) {e.printStackTrace();}
            ints.add(new int[1024 * 1024]);
        }
    }
}
