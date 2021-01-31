package 设计模式.自定义SpringIoc.com.like.framework.beans.factory.support;

import 设计模式.自定义SpringIoc.com.like.framework.beans.BeanDefinition;

/**
 * @author like
 * @date 2021-01-08 17:17
 * @contactMe 980650920@qq.com
 * @description 注冊表對象
 */
public interface BeanDefinitionRegistry {

    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

    void removeBeanDefinition(String beanName) throws Exception;

    BeanDefinition getBeanDefinition(String beanName) throws Exception;

    boolean containsBeanDefinition(String beanName);

    int getBeanDefinitionCount();

    String[] getBeanDefinitionNames();
}
