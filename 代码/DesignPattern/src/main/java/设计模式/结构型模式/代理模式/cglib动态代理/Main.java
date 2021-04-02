package 设计模式.结构型模式.代理模式.cglib动态代理;

/**
 * @author like
 * @date 2020-12-17 17:58
 * @contactMe 980650920@qq.com
 * @description
 */
public class Main {
    public static void main(String[] args) {
        TrainStation proxy = ProxyFactory.getProxy();
        proxy.sell();
    }
}
