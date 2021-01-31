package 设计模式.自定义SpringIoc.com.like.framework.beans.factory;

/**
 * @author like
 * @date 2021-01-08 19:02
 * @contactMe 980650920@qq.com
 * @description ioc容器顶层接口
 */
public interface BeanFactory {

    Object getBean(String name) throws Exception;

    <T> T getBean(String name, Class<? extends T> clazz) throws Exception;
}
