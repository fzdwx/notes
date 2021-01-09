package 设计模式.自定义SpringIoc.com.like.framework.context;

import 设计模式.自定义SpringIoc.com.like.framework.beans.factory.BeanFactory;

/**
 * @author like
 * @date 2021-01-08 19:06
 * @contactMe 980650920@qq.com
 * @description 定义非延时加载功能
 */
public interface ApplicationContext extends BeanFactory {

    void refresh() throws Exception;
}
