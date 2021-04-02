package 设计模式.自定义SpringIoc.com.like.framework.beans;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author like
 * @date 2021-01-08 16:51
 * @contactMe 980650920@qq.com
 * @description 用来存储和管理多个propertyValue对象
 */
public class MutablePropertyValues implements Iterable<PropertyValue> {

    private final List<PropertyValue> pv;

    public MutablePropertyValues() {pv = new ArrayList<>();}

    public MutablePropertyValues(List<PropertyValue> pv) {
        if (pv == null) {
            pv = new ArrayList<>();
        }
        this.pv = pv;
    }

    public PropertyValue[] getPropertyValues() {
        return pv.toArray(new PropertyValue[0]);
    }

    public PropertyValue getPropertyValue(String propertyName) {
        for (PropertyValue p : pv) {
            if (p.getName().equals(propertyName)) {
                return p;
            }
        }
        return null;
    }

    public MutablePropertyValues addPropertyValue(PropertyValue pv) {
        for (int i = 0; i < this.pv.size(); i++) {
            if (this.pv.get(i).getName().equals(pv.getName())) {
                this.pv.set(i, pv);
                return this;
            }
        }
        this.pv.add(pv);
        return this;
    }

    public boolean contains(String propertyName) {
        return getPropertyValue(propertyName) != null;
    }

    public boolean isEmpty() {
        return pv.isEmpty();
    }

    @Override
    public Iterator<PropertyValue> iterator() {
        return pv.iterator();
    }
}
