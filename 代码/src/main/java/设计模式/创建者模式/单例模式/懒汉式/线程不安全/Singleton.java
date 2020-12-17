package 设计模式.创建者模式.单例模式.懒汉式.线程不安全;

/**
 * @author like
 * @date 2020-12-12 14:31
 * @contactMe 980650920@qq.com
 * @description
 */
public class Singleton {
    private Singleton() {
    }

    private static Singleton instance;

    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
