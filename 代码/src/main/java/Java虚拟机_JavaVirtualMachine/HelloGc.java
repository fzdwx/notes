package Java虚拟机_JavaVirtualMachine;

/**
 * @author likeLove
 * @since 2020-09-26  18:46
 * 如何查看一个java程序，他的jvm参数是多少
 *  jps -l
 *  jinfo -flag PrintGCDetails 25812
 *  -XX:-PrintGCDetails
 */
public class HelloGc {

    public static void main(String[] args) {
        System.out.println("Runtime.getRuntime().maxMemory() = " + Runtime.getRuntime().maxMemory());
    }

}
