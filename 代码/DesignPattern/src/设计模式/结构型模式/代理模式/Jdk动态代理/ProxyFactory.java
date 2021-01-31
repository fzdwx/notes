package 设计模式.结构型模式.代理模式.Jdk动态代理;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author like
 * @date 2020-12-17 17:23
 * @contactMe 980650920@qq.com
 * @description
 */
public class ProxyFactory {
    private static TrainStation station = new TrainStation();

    public static SellTickets getProxy() {
        /**
         * ClassLoader loader     需要代理的类
         * Class<?>[] interfaces  代理类实现的接口
         * InvocationHandler h    代理对象的调用处理程序
         */
        return (SellTickets) Proxy.newProxyInstance(
                TrainStation.class.getClassLoader(),
                TrainStation.class.getInterfaces(),
                new InvocationHandler() {
                    /**
                     * 调用
                     *
                     * @param proxy  代理对象
                     * @param method 接口中的方法
                     * @param args   arg游戏
                     * @return 方法的返回值
                     */
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        method.invoke(station,args);  // 执行哪个对象的方法
                        System.out.println("代理收取服务费(jdk动态代理)");
                        return null;
                    }
                });
    }
}
