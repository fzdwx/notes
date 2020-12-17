package Java虚拟机_JavaVirtualMachine;

/**
 * @author likeLove
 * @since 2020-09-27  11:13
 */
public class OOMErrorDemo {
    public static void main(String[] args) {

    }

    private static void outOfMemoryJavaHeapSpace() {
        //假如 -Xms 10m -Xmx 10m
        //new 一个字节数组占用 20m
        //这时候就会报 out of memoryerror java heap space
        byte[] bytes = new byte[20 * 1024 * 1024];
    }

    public static void stackOverflowError() {
        stackOverflowError();
    }

}
