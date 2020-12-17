package Java虚拟机_JavaVirtualMachine.runttimedata;

import java.util.HashSet;

/**
 * @author likeLove
 * @since 2020-09-29  10:57
 */
public class JVMStackDemo {
    public static void main(String[] args) {
        JVMStackDemo js = new JVMStackDemo();
        js.a();
        HashSet<Object> objects = new HashSet<>();
    }
    public void  a (){
        int a = 10;
        System.out.println("a方法被调用");
        b();
    }
    public void  b (){
        int b = 20;
        System.out.println("b方法被调用");
    }
}
