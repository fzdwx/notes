package 设计模式.创建者模式.单例模式.懒汉式.双重检查;

/**
 * @author like
 * @date 2020-12-12 14:34
 * @contactMe 980650920@qq.com
 * @description
 */
public class Singleton {

    private Singleton() { }

    private static volatile Singleton instance;

    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
