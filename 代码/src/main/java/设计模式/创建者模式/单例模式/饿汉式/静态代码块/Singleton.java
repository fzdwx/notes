package 设计模式.创建者模式.单例模式.饿汉式.静态代码块;

/**
 * @author like
 * @date 2020-12-12 14:30
 * @contactMe 980650920@qq.com
 * @description
 */
public class Singleton {
    private Singleton() {}
    private static Singleton instance;

    static {
        instance = new Singleton();
    }

    public static Singleton getInstance() {
        return instance;
    }
}
