package 数据结构和算法.数据结构.queue;

/**
 * @author like
 * @date 2020-12-14 12:15
 * @contactMe 980650920@qq.com
 * @description
 */
public class CircleDeque<T> {


    private T[] element;
    private Integer size = 0;
    private Integer frontIndex = 0;

    private Integer defaultCapacity = 10;

    public CircleDeque() {
        this(10);
    }

    public CircleDeque(Integer capacity) {
        this.defaultCapacity = capacity;
        element = (T[]) new Object[defaultCapacity];
    }

    public Integer size() {
        return size;
    }


    public boolean isEmpty() {
        return size == 0;
    }


    public void enQueueRear(T o) {
        expansion(size + 1);
        element[index(size)] = o;
        size++;
    }


    public T deQueueFront() {
        T res = element[frontIndex];
        element[frontIndex] = null;
        frontIndex = index(1);

        size--;
        return res;
    }

    public T front() {
        return element[frontIndex];
    }


    public void enQueueFront(T o) {
        expansion(size + 1);

        frontIndex = index(-1);
        size++;

        element[frontIndex] = o;
    }

    public T deQueueRear() {
        int index = index(size - 1);

        T res = element[index];
        element[index] = null;

        size--;
        return res;
    }

    public T rear() {
        return element[index(size - 1)];
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
}
