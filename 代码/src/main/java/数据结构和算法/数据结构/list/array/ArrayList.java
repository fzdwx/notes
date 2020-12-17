package 数据结构和算法.数据结构.list.array;

import 数据结构和算法.数据结构.list.AbstractList;
import 数据结构和算法.数据结构.list.List;


/**
 * @author likeLove
 * @since 2020-09-29  20:49
 */
public class ArrayList<T> extends AbstractList<T> implements List<T> {

    /**
     * 存放元素的数组
     */
    private T[] elements;

    /**
     * 默认数组大小
     */
    public static final int DEFAULT_CAPACITY = 10;

    /**
     * 初始化数组大小
     *
     * @param capacity 初始化容量
     */
    public ArrayList(int capacity) {
        //如果传入的数组容量大小  小于  默认的 10，就直接初始化为10
        capacity = Math.max(capacity, DEFAULT_CAPACITY);
        elements = (T[]) new Object[capacity];
    }

    /**
     * Instantiates a new Array list.
     */
    public ArrayList() {
        //如果不传入初始化数组容量
        this(DEFAULT_CAPACITY);
    }


    /**
     * 查找元素在数组中的位置
     *
     * @param o 需要查找的元素
     * @return 元素在数组中的索引
     */
    @Override
    public int indexOf(T o) {
        // 循环遍历,判断 o 是否等于数组中对应的元素
        for (int i = 0; i < size; i++) {
            if (elements[i].equals(o)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 添加元素到数组的最后
     *
     * @param o 需要添加的元素
     */
    @Override
    public void add(T o) {
        //  调用add
        add(size, o);
    }

    /**
     * 添加元素到数组的指定位置
     *
     * @param index 指定位置
     * @param o     元素
     */
    @Override
    public void add(int index, T o) {
        if (o == null) {
            return;
        }
        // 检测index
        checkIndexOfAdd(index);
        // 动态扩容
        expandCapacity(size + 1);

        // 从最后开始遍历，前一个覆盖后一个
        for (int i = size; i > index; i--) {
            elements[i + 1] = elements[i];
        }
        elements[index] = o;
        size++;
    }


    /**
     * 移除指定位置的元素
     *
     * @param index 指定位置
     * @return 被移除的元素
     */
    @Override
    public T remove(int index) {
        // 检查index
        checkIndex(index);
        // 检查是否需要缩容
        trimCapacity();
        // 保存要删除的元素
        T remove = elements[index];

        // 从index开始遍历，后一个覆盖前一个
        for (int i = index; i < size; i++) {
            elements[i] = elements[i + 1];
        }

        // 删除最后一个
        elements[size] = null;
        size--;
        return remove;
    }

    /**
     * 移除指定元素
     *
     * @param o o
     * @return 被移除的元素
     */
    @Override
    public T remove(T o) {
        if (o == null) {
            return null;
        }
        int index = indexOf(o);
        return remove(index);
    }

    /**
     * 根据索引返回元素(从0开始）
     *
     * @param index 元素的索引
     * @return 元素
     */
    @Override
    public T get(int index) {
        //判断字符是否在正常范围之内
        checkIndex(index);
        return elements[index];
    }


    /**
     * 为索引位置设置新的元素
     *
     * @param index 索引
     * @param o     新元素
     * @return 旧元素
     */
    @Override
    public T set(int index, T o) {
        // 检查index是否合法
        checkIndex(index);
        //取出原有的元素
        T old = elements[index];
        elements[index] = o;
        return old;
    }

    /**
     * 清空数组
     */
    @Override
    public void clear() {
        elements = (T[]) new Object[elements.length];
        size = 0;
    }

    /**
     * 动态扩容
     * 不需要缩容的情况
     * 旧容量大于传入的需要的容量
     *
     * @param needCapacity 需要的容量
     */
    private void expandCapacity(int needCapacity) {
        int oldCapacity = elements.length;
        //不需要缩容
        if (oldCapacity >= needCapacity) {
            return;
        }

        //扩容
        int newCapacity = (oldCapacity >> 1) + oldCapacity;
        Object[] newElements = new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[i];
        }

        //引用
        elements = (T[]) newElements;
    }

    /**
     * Trim capacity. 动态缩容
     * 不需要缩容的情况：
     * 1.实用的空间超过总容量的一半
     * 2.使用的空间小于默认的容量
     * <p>
     * size：已经使用的容量
     * capacity：总容量
     */
    private void trimCapacity() {
        int oldCapacity = elements.length;
        int newCapacity = (oldCapacity >> 1) + 1;
        if (size >= newCapacity || oldCapacity <= DEFAULT_CAPACITY) {
            return;
        }

        //缩容
        Object[] newElements = new Object[newCapacity + 1];
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[i];
        }

        //引用
        elements = (T[]) newElements;
        System.out.println(oldCapacity + "缩容 " + newCapacity);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb./*append("size = ").append(size).*/append("[");
        for (int i = 0; i < size; i++) {
            sb.append(elements[i]);
            if (i < size - 1) {
                sb.append(",").append(" ");
            }
        }
        sb.append("]");
        return String.valueOf(sb);
    }
}
