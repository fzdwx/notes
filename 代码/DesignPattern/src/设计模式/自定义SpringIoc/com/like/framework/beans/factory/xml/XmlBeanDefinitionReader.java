package 设计模式.自定义SpringIoc.com.like.framework.beans.factory.xml;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import 设计模式.自定义SpringIoc.com.like.framework.beans.BeanDefinition;
import 设计模式.自定义SpringIoc.com.like.framework.beans.MutablePropertyValues;
import 设计模式.自定义SpringIoc.com.like.framework.beans.PropertyValue;
import 设计模式.自定义SpringIoc.com.like.framework.beans.factory.support.BeanDefinitionReader;
import 设计模式.自定义SpringIoc.com.like.framework.beans.factory.support.BeanDefinitionRegistry;
import 设计模式.自定义SpringIoc.com.like.framework.beans.factory.support.SimpleBeanDefinitionRegistry;

import java.util.List;

/**
 * @author like
 * @date 2021-01-08 18:39
 * @contactMe 980650920@qq.com
 * @description 针对xml格式
 */
public class XmlBeanDefinitionReader implements BeanDefinitionReader {

    private BeanDefinitionRegistry registry;

    public XmlBeanDefinitionReader() {
        registry = new SimpleBeanDefinitionRegistry();
    }

    @Override
    public BeanDefinitionRegistry getRegistry() {
        return registry;
    }

    @Override
    public void loadBeanDefinition(String configLocation) throws Exception {
        // 使用dom4j进行xml解析
        SAXReader reader = new SAXReader();
        // 1.获取类路径下的配置文件
        Document doc = reader.read(this.getClass().getClassLoader().getResourceAsStream(configLocation));
        // 2.根据doc获取标签对象
        Element re = doc.getRootElement();
        List<Element> bes = re.elements("bean");
        for (Element e : bes) {
            // 3.获取id属性
            String id = e.attributeValue("id");
            // 4.获取class属性
            String clazz = e.attributeValue("class");
            // 5.将id属性和class属性封装到beanDefinition对象中
            BeanDefinition beanDefinition = new BeanDefinition();
            beanDefinition.setId(id);
            beanDefinition.setClassName(clazz);
            // 6.获取bean标签中的property标签
            MutablePropertyValues pvs = new MutablePropertyValues();
            List<Element> ps = e.elements("property");
            for (Element p : ps) {
                // 7.封装成propertyValue对象
                PropertyValue propertyValue = new PropertyValue(p.attributeValue("name"),p.attributeValue("ref"), p.attributeValue("value"));
                pvs.addPropertyValue(propertyValue);
            }
            // 7.将property封装到beanDefinition对象中
            beanDefinition.setPvs(pvs);
            // 8.将beanDefinition注册到registry中
            registry.registerBeanDefinition(id,beanDefinition);
        }
    }
}
