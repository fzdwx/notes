package 数据结构和算法.算法.排序.sort;

/**
 * @author like
 * @date 2020-12-30 10:44
 * @contactMe 980650920@qq.com
 * @description
 */
public class InsertSort2<E extends Comparable<E>> extends Sort<E> {

    @Override
    protected E[] sort() {
        for (int start = 1; start < array.length; start++) {        // 1.假设排好序的数组是 array[0]
            int cur = start;                                       // 2.记录取出元素的index
            E unSort = array[cur];                                // 3.记录取出未排序数组中的元素
            while (cur > 0 && cmp(unSort, array[cur - 1]) < 0) {   // 4.比较未排序元素，和已经排序的元素
                array[cur] = array[cur - 1];                    // 5.如果小于就一直交换，直到大于
                cur--;
            }
            array[cur] = unSort;                             // 6.最终排序的位置
        }
        return array;
    }
}
