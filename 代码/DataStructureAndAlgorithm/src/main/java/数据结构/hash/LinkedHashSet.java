package 数据结构.hash;

import 数据结构.set.Set;

/**
 * @author like
 * @date 2020-12-26 14:30
 * @contactMe 980650920@qq.com
 * @description
 */
public class LinkedHashSet<T> implements Set<T> {
    public static final Object VALUE = new Object();
    private final HashMap<T, Object> map = new LinkedHashMap<>();

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public boolean contains(T element) {
        return map.containsKey(element);
    }

    @Override
    public void add(T element) {
        map.put(element, VALUE);
    }

    @Override
    public void remove(T element) {
        map.remove(element);
    }

    @Override
    public void traversal(Visitor<T> visitor) {
        map.traversal((key, value) -> visitor.visit(key));
    }
}
