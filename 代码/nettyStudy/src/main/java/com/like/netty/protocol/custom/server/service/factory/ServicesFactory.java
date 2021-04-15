package com.like.netty.protocol.custom.server.service.factory;

import com.like.netty.protocol.custom.config.Config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * service 创建工厂
 * <p>
 * 从配置文件中读取对应类的实现类
 * /resource/application.setting
 */
public class ServicesFactory {


    static Map<Class<?>, Object> map = new ConcurrentHashMap<>();

    /*
    *  启动的时候读取所有service 并创建对应实现类
    * */
    static {
        Config.getSetting().keySet().forEach(key -> {
            try {
                if (key.endsWith("Service")) {
                    Class<?> interfaceClass = Class.forName(key);
                    Class<?> instanceClass = Class.forName(Config.getSetting().get(key));
                    map.put(interfaceClass, instanceClass.newInstance());
                }
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 得到服务
     *
     * @param interfaceClass 接口类
     * @return {@link T}
     */
    public static <T> T getService(Class<T> interfaceClass) {
        return (T) map.get(interfaceClass);
    }
}
