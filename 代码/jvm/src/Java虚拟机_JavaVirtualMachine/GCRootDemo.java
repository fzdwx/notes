package Java虚拟机_JavaVirtualMachine;

/**
 * @author likeLove
 * @since 2020-09-26  18:24
 * 可以作为gc root的对象
 * 1.虚拟机中引用的对象（栈 帧中的本地变量表）
 * 2.方法区中的类静态属性引用的对象
 * 3.方法去中常量引用的对象
 * 4.本地方法栈中JNI（native 方法）中引用的对象
 */
public class GCRootDemo {
    private byte[] bytes = new byte[100 * 1024 * 1024];

    public static void m1 (){
        GCRootDemo r1 = new GCRootDemo();
        System.gc();
        System.out.println("第一次gc完成");
    }

    public static void main(String[] args) {
        m1();
    }
}
