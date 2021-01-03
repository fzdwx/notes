package 数据结构和算法.算法.排序.sort.cmp;

import 数据结构和算法.算法.排序.sort.Sort;

/**
 * @author like
 * @date 2020-12-28 10:20
 * @contactMe 980650920@qq.com
 * @description 选择排序
 */
public class SelectionSort<E extends Comparable<E>> extends Sort<E> {

    @Override
    protected E[] sort() {
        for (int i = 0; i < array.length; i++) {
            int maxIndex = i;
            for (int j = i; j < array.length; j++) {
                if (cmp(maxIndex, j) > 0) {
                    maxIndex = j;
                }
            }
            swap(maxIndex, i);
        }
        return array;
    }
}
