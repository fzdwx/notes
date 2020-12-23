package 数据结构和算法.数据结构.set;

import 数据结构和算法.数据结构.list.linked.LinkedList;

/**
 * @author like
 * @date 2020-12-23 10:24
 * @contactMe 980650920@qq.com
 * @description 链表实现的Set
 */
public class ListSet<T> implements Set<T> {
    private final LinkedList<T> list = new LinkedList<>();

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public boolean contains(T element) {
        return list.contains(element);
    }

    @Override
    public void add(T element) {
        int index = list.indexOf(element);
        if (index != -1 && list.get(index) != null) {
            list.set(index, element);
        } else {
            list.add(element);
        }
    }

    @Override
    public void remove(T element) {
        int index = list.indexOf(element);
        if (index != -1) {
            list.add(element);
        }
    }

    @Override
    public void traversal(Visitor<T> visitor) {
        if (visitor == null) {
            return;
        }
        int length = list.size();
        for (int i = 0; i < length; i++) {
            boolean visit = visitor.visit(list.get(i));
            if (visit) { return; }
        }
    }
}
