package 设计模式.自定义SpringIoc.com.like.framework.beans.factory.support;

/**
 * @author like
 * @date 2021-01-08 18:37
 * @contactMe 980650920@qq.com
 * @description 用于解析配置文件
 */
public interface BeanDefinitionReader {

    BeanDefinitionRegistry getRegistry();

    void loadBeanDefinition(String configLocation) throws Exception;
}
