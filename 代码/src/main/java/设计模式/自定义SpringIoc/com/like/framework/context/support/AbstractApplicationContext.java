package 设计模式.自定义SpringIoc.com.like.framework.context.support;

import 设计模式.自定义SpringIoc.com.like.framework.beans.factory.support.BeanDefinitionReader;
import 设计模式.自定义SpringIoc.com.like.framework.beans.factory.support.BeanDefinitionRegistry;
import 设计模式.自定义SpringIoc.com.like.framework.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * @author like
 * @date 2021-01-08 19:10
 * @contactMe 980650920@qq.com
 * @description ApplicationContext的子实现，用于立即加载
 */
public abstract class AbstractApplicationContext implements ApplicationContext {
    // 解析器
    protected BeanDefinitionReader beanDefinitionReader;

    // 存放bean对象的map容器
    protected Map<String, Object> singletonObjs = new HashMap<>();

    // 记录配置文件路径的变量
    protected String configLocationPath;

    @Override
    public void refresh() throws Exception {
        // 1.加载beanDefinition对象
        beanDefinitionReader.loadBeanDefinition(configLocationPath);
        // 2.创建bean初始化
        finishBeanInitialization();
    }

    /**
     * 完成bean初始化
     */
    private void finishBeanInitialization() throws Exception {
        BeanDefinitionRegistry registry = beanDefinitionReader.getRegistry();
        String[] beanDefinitionNames = registry.getBeanDefinitionNames();
        for (String name : beanDefinitionNames) {
            getBean(name);
        }
    }
}
