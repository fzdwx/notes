package Java虚拟机_JavaVirtualMachine.runttimedata;

/**
 * @author likeLove
 * @since 2020-09-30  9:31
 * java虚拟机栈-栈帧-局部变量表
 */
public class LocalVariablesDemo {
    private  int  count = 0;

    public static void main(String[] args) {
        LocalVariablesDemo lvd = new LocalVariablesDemo();
        int num = 10;
        lvd.test1();
        lvd.test2();
    }

    private void test1() {
        String name = "like";
    }
    public void  test2 (){
        int a = 3;
        int b = 4;
        int i  = b++ +  ++a;
        System.out.println("i = " + i);
    }
}
