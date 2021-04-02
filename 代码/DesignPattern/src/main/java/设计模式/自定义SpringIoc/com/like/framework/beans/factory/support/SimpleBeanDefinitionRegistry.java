package 设计模式.自定义SpringIoc.com.like.framework.beans.factory.support;

import 设计模式.自定义SpringIoc.com.like.framework.beans.BeanDefinition;

import java.util.HashMap;
import java.util.Map;

/**
 * @author like
 * @date 2021-01-08 17:19
 * @contactMe 980650920@qq.com
 * @description
 */
public class SimpleBeanDefinitionRegistry implements BeanDefinitionRegistry {

    private Map<String, BeanDefinition> data = new HashMap<>();

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        data.put(beanName,beanDefinition);
    }

    @Override
    public void removeBeanDefinition(String beanName) throws Exception {
            data.remove(beanName);
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) throws Exception {
        return data.get(beanName);
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return data.containsKey(beanName);
    }

    @Override
    public int getBeanDefinitionCount() {
        return data.size();
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return data.keySet().toArray(new String[0]);
    }
}
