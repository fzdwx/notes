package 设计模式.创建者模式.单例模式.饿汉式.静态成员变量;

/**
 * @author like
 * @date 2020-12-12 14:20
 * @contactMe 980650920@qq.com
 * @description 静态成员变量
 */
public class Singleton {

    // 1.私有化构造器
    private Singleton() {
    }

    // 2.供外界使用的本类对象
    private static final Singleton INSTANCE = new Singleton();

    // 3.提供外界访问的方法，让外界获取对象
    public static Singleton getInstance() {
        return INSTANCE;
    }
}
