package 数据结构.queue;


import java.util.Arrays;

/**
 * @author like
 * @date 2020-12-14 11:05
 * @contactMe 980650920@qq.com
 * @description 循环队列
 */
public class CircleQueue<T> {

    private T[] element;
    private Integer size = 0;
    private Integer frontIndex = 0;
    private Integer defaultCapacity = 10;

    public CircleQueue() {
        this(10);
    }

    public CircleQueue(Integer capacity) {
        this.defaultCapacity = capacity;
        element = (T[]) new Object[defaultCapacity];
    }

    public static void main(String[] args) {
        CircleQueue<Integer> queue = new CircleQueue<Integer>();
        for (int i = 0; i < 10; i++) {
            queue.enQueue(i);
        }
        for (int i = 0; i < 5; i++) {
            queue.deQueue();
        }
        for (int i = 0; i < 20; i++) {
            queue.enQueue(i);
        }
        System.out.println(queue);
        while (!queue.isEmpty()) {
            System.out.println(queue.deQueue());
        }
    }

    public Integer size() {
        return size;
    }


    public boolean isEmpty() {
        //        return size %defaultCapacity == 0;
        return size == 0;
    }

    public void enQueue(T o) {
        expansion(size + 1);
        element[index(size)] = o;
        size++;
    }

    public T deQueue() {
        T res = element[frontIndex];
        element[frontIndex] = null;
        frontIndex = index(1);
        size--;
        return res;
    }

    public T front() {
        return element[frontIndex];
    }

    @Override
    public String toString() {
        return "CircleQueue{" +
                "element=" + Arrays.toString(element) +
                ", size=" + size +
                ", frontIndex=" + frontIndex +
                ", defaultCapacity=" + defaultCapacity +
                '}';
    }

    private void expansion(int needCapacity) {
        if (defaultCapacity >= needCapacity) {
            return;
        }
        int oldCapacity = element.length;
        int newCapacity = (defaultCapacity >> 1) + defaultCapacity;
        T[] newElement = (T[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newElement[i] = element[index(i)];
        }

        element = newElement;
        defaultCapacity = needCapacity;
        frontIndex = 0;

    }

    /**
     * 找到i位置的元素的索引
     */
    private int index(int i) {
        i += frontIndex;
        if (i < 0) {
            i += defaultCapacity;
        }
        //        return i % defaultCapacity;
        return i - (i >= defaultCapacity ? defaultCapacity : 0);
    }
}
