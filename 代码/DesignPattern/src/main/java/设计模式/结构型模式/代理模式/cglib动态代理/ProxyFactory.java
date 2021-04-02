package 设计模式.结构型模式.代理模式.cglib动态代理;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author like
 * @date 2020-12-17 17:23
 * @contactMe 980650920@qq.com
 * @description
 */
public class ProxyFactory implements MethodInterceptor {

    public  static TrainStation getProxy() {
        Enhancer enhancer = new Enhancer();
        // 1.设置要代理的类
        enhancer.setSuperclass(TrainStation.class);
        // 2.设置回调函数
        enhancer.setCallback(new ProxyFactory());
        // 3.创建代理对象
        return (TrainStation) enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("cglib动态代理");
        method.invoke(new TrainStation(),objects);
        return null;
    }
}
