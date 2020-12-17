package 数据结构和算法.数据结构.queue;

import 数据结构和算法.数据结构.list.List;
import 数据结构和算法.数据结构.list.linked.LinkedList;

/**
 * @author like
 * @date 2020-12-14 9:55
 * @contactMe 980650920@qq.com
 * @description
 */
public class Queue<T> {

    public static void main(String[] args) {
        Queue<Integer> queue = new Queue<>();
        queue.enQueue(11);
        queue.enQueue(22);
        queue.enQueue(33);
        queue.enQueue(44);
        while (!queue.isEmpty()) {
            System.out.println(queue.deQueue());
        }
    }

    private List<T> list;


    public Queue() {
        list = new LinkedList<>();
    }

    public Queue(List<T> list) {
        this.list = list;
    }

    public Integer size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public void enQueue(T o) {
        list.add(o);
    }

    public T deQueue() {
        return list.remove(0);
    }

    public T front() {
        return list.get(0);
    }
}
