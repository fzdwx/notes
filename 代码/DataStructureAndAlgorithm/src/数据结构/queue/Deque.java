package 数据结构.queue;

import 数据结构.list.List;
import 数据结构.list.linked.LinkedList;

/**
 * @author like
 * @date 2020-12-14 10:55
 * @contactMe 980650920@qq.com
 * @description
 */
public class Deque<T> {
    private List<T> list;


    public Deque() {
        list = new LinkedList<>();
    }

    public Deque(List<T> list) {
        this.list = list;
    }

    public Integer size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public void enQueueRear(T o) {
        list.add(o);
    }

    public void enQueueFront(T o) {
        list.add(0, o);
    }

    public T deQueueFront() {
        return list.remove(0);
    }

    public T deQueueRear() {
        return list.remove(size() - 1);
    }

    public T front() {
        return list.get(0);
    }

    public T rear() {
        return list.get(size() - 1);
    }
}
