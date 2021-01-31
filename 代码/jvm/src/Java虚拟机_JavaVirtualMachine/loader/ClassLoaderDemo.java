package Java虚拟机_JavaVirtualMachine.loader;

/**
 * @author likeLove
 * @since 2020-09-27  21:07
 */
public class ClassLoaderDemo {
    private static int i = 0;

    static {
        i = 2;
    }

    public static void main(String[] args) {
        //获取类加载的方式
        try {
            ClassLoader classLoader = Class.forName("java.lang.String").getClassLoader();
            System.out.println("classLoader = " + classLoader);

            ClassLoader classLoader1 = Thread.currentThread().getContextClassLoader();
            System.out.println("classLoader1 = " + classLoader1);

            ClassLoader classLoader2 = ClassLoader.getSystemClassLoader();
            System.out.println("classLoader2 = " + classLoader2);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void t1() {
        String s = "hello";
        System.out.println(s);
    }
}

