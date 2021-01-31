package 设计模式.自定义SpringIoc.com.like.framework.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author like
 * @date 2021-01-08 16:48
 * @contactMe 980650920@qq.com
 * @description 用来封装bean标签下的property标签的属性 name ref value
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropertyValue {

    private String name;
    private String ref;
    private String value;
}
