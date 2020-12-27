package 数据结构和算法.数据结构.heap;

import 数据结构和算法.数据结构.tree.printer.BinaryTreeInfo;

import java.util.Comparator;

/**
 * @author like
 * @date 2020-12-27 9:51
 * @contactMe 980650920@qq.com
 * @description
 */
public class BinaryHeap<E> extends AbstractHeap<E> implements Heap<E>, BinaryTreeInfo {
    public static final int DEFAULT_CAPACITY = 10;
    public E[] elements;

    public BinaryHeap() {
        this(DEFAULT_CAPACITY, null);
    }

    public BinaryHeap(int capacity, Comparator<E> comparator) {
        if (capacity < DEFAULT_CAPACITY) capacity = DEFAULT_CAPACITY;
        this.comparator = comparator;
        this.elements = (E[]) new Object[capacity];
    }

    public BinaryHeap(int capacity) {
        this(capacity, null);
    }

    public BinaryHeap(E[] elements) {
        this(elements, null);
    }

    public BinaryHeap(E[] elements, Comparator<E> comparator) {
        if (elements == null || elements.length == 0) {
            elements = (E[]) new Object[DEFAULT_CAPACITY];
        }
        this.elements = (E[]) new Object[Math.max(DEFAULT_CAPACITY, elements.length)];
        size = elements.length;
        for (int i = 0; i < elements.length; i++) {
            this.elements[i] = elements[i];
        }
        heapify();
        this.comparator = comparator;
    }

    public BinaryHeap<E> topN(int n) {
        if (n > size) return null;
        BinaryHeap<E> h = new BinaryHeap<>();
        for (E e : elements) {
            if (h.size < n) {   // 先存放 前n个数
                h.add(e);
            } else if (e != null && compare(e, h.get()) > 0) {
                // 如果 遍历的元素 大于 我们堆中的堆顶，就替换
                h.replace(e);
            }
        }
        return h;
    }

    /**
     * 清晰的
     */
    @Override
    public void clear() {
        size = 0;
        for (E element : elements) {
            element = null;
        }
    }

    /**
     * 添加
     *
     * @param element 元素
     */
    @Override
    public void add(E element) {
        elementNollPointEx(element);
        resize(size + 1);

        elements[size++] = element;
        siftUp(size - 1);// 让添加的节点上滤
    }

    /**
     * 获得堆顶元素
     *
     * @return {@link E}
     */
    @Override
    public E get() {
        return size == 0 ? null : elements[0];
    }

    /**
     * 删除堆顶元素
     *
     * @return {@link E}
     */
    @Override
    public E remove() {
        if (size == 0) throw new RuntimeException("size:" + size + ",index:0");
        E removeNode = elements[0]; // root就是要刪除的
        int last = --size;

        // last 调到第一个
        elements[0] = elements[last];
        elements[last] = null;   // 清空

        // 下滤
        siftDown(0);
        return removeNode;
    }

    /**
     * 删除堆顶元素的同时插入一个新元素
     *
     * @param e 元素
     * @return {@link E}
     */
    @Override
    public E replace(E e) {
        E remove = null;
        if (size == 0) {
            elements[0] = e;
            size++;
        } else {
            remove = elements[0];
            elements[0] = e;  // 添加
            siftDown(0);  // 下溢
        }
        return remove;
    }

    @Override
    public Object root() {
        return 0;
    }

    @Override
    public Object left(Object node) {
        int i = ((Integer) node << 1) + 1;
        return i >= size ? null : i;
    }

    @Override
    public Object right(Object node) {
        int i = ((Integer) node << 1) + 2;
        return i >= size ? null : i;
    }

    @Override
    public Object string(Object node) {
        return elements[((Integer) node)];
    }

    private void elementNollPointEx(E element) {
        if (element == null) { throw new RuntimeException("不能添加null 元素");}
    }

    /**
     * 批量建堆
     * 1.自上而下的 上滤
     * 2.自下而上的 下滤
     */
    private void heapify() {
        // 1.自上而下的 上滤
        //        for (int i = 0; i < size; i++) {
        //            siftUp(i);
        //        }
        // 2.自下而上的 下滤
        for (int i = (size >> 1) - 1; i >= 0; i--) {
            siftDown(i);
        }
    }

    private void resize(int i) {

    }

    private void siftDown(int index) {
        E e = elements[index];
        int half = size >> 1; // 第一个叶子节点的索引 = 非叶子节点的数量 index * 2 <= size || (index * 2) <= size - 1
        while (index < half) {  // 有子节点才进入
            // 找出最大的子节点
            int cIndex = (index << 1) + 1;
            E c = elements[cIndex];
            int crIndex = cIndex + 1;
            if (crIndex < size && compare(elements[crIndex], c) > 0) {
                c = elements[cIndex = crIndex];
            }

            // 交换e，c
            if (compare(e, c) >= 0) break;
            elements[index] = c;
            index = cIndex;
        }
        elements[index] = e;
    }

    /**
     * 上滤
     *
     * @param index 指数
     */
    private void siftUp(int index) {
        E e = elements[index];  // 需要上滤的节点
        while (index > 0) {
            int pIndex = (index - 1) / 2; // 父节点的索引
            E p = elements[pIndex];      // 父节点
            if (compare(e, p) <= 0) break;  // 小于等于父节点

            // e > p
            elements[index] = p;

            // 记录 e最后的位置
            index = pIndex;
        }
        elements[index] = e;
    }
}
