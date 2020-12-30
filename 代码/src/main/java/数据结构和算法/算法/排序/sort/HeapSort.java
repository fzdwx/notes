package 数据结构和算法.算法.排序.sort;

/**
 * @author like
 * @date 2020-12-28 10:40
 * @contactMe 980650920@qq.com
 * @description 堆排序
 */
public class HeapSort<E extends Comparable<E>> extends Sort<E> {

    private int heapSize;

    @Override
    protected E[] sort() {
        // 1.建堆
        heapSize = array.length;
        for (int i = (heapSize >> 1) - 1; i >= 0; i--) {
            siftDown(i);
        }
        while (heapSize > 1) {
            // 2.交换堆顶的堆尾的元素
            swap(0, --heapSize);
            // 3.恢复堆的性质
            siftDown(0);
        }

        return array;
    }

    private void siftDown(int index) {
        E e = array[index];
        int half = heapSize >> 1; // 第一个叶子节点的索引 = 非叶子节点的数量 index * 2 <= size || (index * 2) <= size - 1
        while (index < half) {  // 有子节点才进入
            // 找出最大的子节点
            int cIndex = (index << 1) + 1;
            E c = array[cIndex];
            int crIndex = cIndex + 1;
            if (crIndex < heapSize && cmp(array[crIndex], c) > 0) {
                c = array[cIndex = crIndex];
            }

            // 交换e，c
            if (cmp(e, c) >= 0) break;
            array[index] = c;
            index = cIndex;
        }
        array[index] = e;
    }
}
