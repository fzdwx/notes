package 设计模式.自定义SpringIoc.com.like.framework.beans;

import lombok.Data;

/**
 * @author like
 * @date 2021-01-08 17:13
 * @contactMe 980650920@qq.com
 * @description 用來封裝bean標簽數據 id class property
 */
@Data
public class BeanDefinition {

    private String id;
    private String className;
    private MutablePropertyValues pvs;

    public BeanDefinition() {
        pvs = new MutablePropertyValues();
    }
}
