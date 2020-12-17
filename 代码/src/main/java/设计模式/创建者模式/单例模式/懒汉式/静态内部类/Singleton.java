package 设计模式.创建者模式.单例模式.懒汉式.静态内部类;

/**
 * @author like
 * @date 2020-12-12 14:38
 * @contactMe 980650920@qq.com
 * @description
 */
public class Singleton {

    private Singleton() {

    }

    private static class SingletonCreate {
        private static final Singleton instance = new Singleton();
    }

    public static Singleton getInstance() {
        return SingletonCreate.instance;
    }
}
