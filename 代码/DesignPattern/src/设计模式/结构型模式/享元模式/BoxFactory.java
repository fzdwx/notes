package 设计模式.结构型模式.享元模式;

import java.util.HashMap;

/**
 * @author like
 * @date 2020-12-24 15:50
 * @contactMe 980650920@qq.com
 * @description
 */
public class BoxFactory {

    private static final BoxFactory BOX_FACTORY = new BoxFactory();
    private  HashMap<String, AbstractBox> map = map = new HashMap<>();

    private BoxFactory() {
        map.put("i", new Ibox());
        map.put("o", new Obox());
        map.put("L", new Lbox());
    }

    public static BoxFactory getInstance() {
        return BOX_FACTORY;
    }

    public AbstractBox getShape(String name) {
        return map.get(name);
    }
}
