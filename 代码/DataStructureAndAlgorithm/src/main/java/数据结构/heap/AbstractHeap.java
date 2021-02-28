package 数据结构.heap;

import java.util.Comparator;

/**
 * @author like
 * @date 2020-12-27 11:21
 * @contactMe 980650920@qq.com
 * @description
 */
public abstract class AbstractHeap<E> implements Heap<E> {
    protected int size;
    protected Comparator<E> comparator;

    public AbstractHeap(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    public AbstractHeap() {
        this(null);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    protected int compare(E e1, E e2) {
        return comparator == null
                ? ((Comparable) e1).compareTo(e2)
                : comparator.compare(e1, e2);
    }
}


