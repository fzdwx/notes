package 数据结构和算法.算法.排序.sort;

/**
 * @author like
 * @date 2020-12-28 10:50
 * @contactMe 980650920@qq.com
 * @description
 */
public abstract class Sort<E> {
    protected E[] array;

    private int cmpCount = 0;
    private int swapCount = 0;

    public E[] sort(E[] array) {
        if (array == null || array.length < 2) throw new RuntimeException("size <2");
        this.array = array;
        return sort();
    }

    protected abstract E[] sort();

    protected int cmp(int i1, int i2) {
        return cmp(array[i1], array[i2]);
    }

    protected int cmp(E e1, E e2) {
        cmpCount++;
        return ((Comparable) e1).compareTo(e2);
    }

    protected void swap(int i1, int i2) {
        swapCount++;

        E temp = array[i1];
        array[i1] = array[i2];
        array[i2] = temp;
    }

}
