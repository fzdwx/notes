package Java虚拟机_JavaVirtualMachine.runttimedata;

/**
 * @author likeLove
 * @since 2020-10-03  9:49
 * -Xms10m -Xmx10m
 */
public class HeapDemo {

    private int id;

    public HeapDemo(int id) {
        this.id = id;
    }

    public static void main(String[] args) {
        show();
      /*  HeapDemo h1 = new HeapDemo(1);
        HeapDemo h2 = new HeapDemo(2);
        int[] arr = new int[]{1, 4, 5};
        Object[] objs = new Object[]{};
        h1.show();
        h2.show();
        Runtime.getRuntime().maxMemory();
        Runtime.getRuntime().totalMemory();
        try {TimeUnit.SECONDS.sleep(1000); } catch (InterruptedException e) {e.printStackTrace();}*/
    }

    private static void show() {
        show();
    }
}
