package 设计模式.自定义SpringIoc.com.like.framework.context.support;

import 设计模式.自定义SpringIoc.com.like.framework.beans.BeanDefinition;
import 设计模式.自定义SpringIoc.com.like.framework.beans.MutablePropertyValues;
import 设计模式.自定义SpringIoc.com.like.framework.beans.PropertyValue;
import 设计模式.自定义SpringIoc.com.like.framework.beans.factory.xml.XmlBeanDefinitionReader;
import 设计模式.自定义SpringIoc.com.like.framework.util.StringUtils;

import java.lang.reflect.Method;

/**
 * @author like
 * @date 2021-01-08 19:19
 * @contactMe 980650920@qq.com
 * @description ioc容器具体的子类
 * 用于加载类路径下的Xml配置文件
 */
public class ClassPathSXmlApplicationContext extends AbstractApplicationContext {
    public ClassPathSXmlApplicationContext(String configLocation) {
        this.configLocationPath = configLocation;
        // 构建解析器对象
        beanDefinitionReader = new XmlBeanDefinitionReader();
        // 进行容器刷新
        try {
            refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object getBean(String name) throws Exception {
        Object o = singletonObjs.get(name);
        if (o != null) return o;
        // 1.获取beanDefinition对象
        BeanDefinition beanDefinition = beanDefinitionReader.getRegistry().getBeanDefinition(name);
        // 2.根据beanDefinition创建对象
        String className = beanDefinition.getClassName();
        Class<?> clazz = Class.forName(className);
        Object bean = clazz.newInstance();
        // 3.进行依赖注入
        MutablePropertyValues pvs = beanDefinition.getPvs();
        for (PropertyValue pv : pvs) {
            String pvName = pv.getName();
            String ref = pv.getRef();
            String value = pv.getValue();
            if (ref != null && !"".equals(ref)) {
                // 获取依赖的bean对象
                Object b = getBean(ref);
                // 拼接方法
                String methodName = StringUtils.getSetterMethodByFieldName(name);
                for (Method method : clazz.getMethods()) {
                    if (method.getName().equals(methodName)) {
                        method.invoke(bean, b);
                    }
                }
            }
            if (value != null && !"".equals(value)) {
                String methodName = StringUtils.getSetterMethodByFieldName(pvName);
                clazz.getMethod(methodName, String.class).invoke(bean, value);
            }
        }
        // 4.放入容器中
        singletonObjs.put(name, bean);
        return bean;
    }

    @Override
    public <T> T getBean(String name, Class<? extends T> clazz) throws Exception {
        Object bean = getBean(name);
        if (bean != null) {
            return clazz.cast(bean);
        }
        return null;
    }
}
