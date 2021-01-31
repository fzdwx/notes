package 设计模式.自定义SpringIoc.com.like.framework.util;

/**
 * @author like
 * @date 2021-01-08 19:36
 * @contactMe 980650920@qq.com
 * @description
 */
public class StringUtils {

    public static String getSetterMethodByFieldName(String fieldName) {
        return "set" +fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
    }
}
