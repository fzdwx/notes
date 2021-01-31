package 算法.排序.sort.cmp;

import 算法.排序.sort.Sort;

/**
 * @author like
 * @date 2020-12-31 9:11
 * @contactMe 980650920@qq.com
 * @description 归并排序
 */
public class MergeSort<E extends Comparable<E>> extends Sort<E> {
    private E[] leftArray;

    @Override
    protected E[] sort() {
        leftArray = (E[]) new Comparable[array.length >> 1];   // [0,mid)
        divide(0, array.length);
        return array;
    }

    /**
     * 对[begin,end])范围内的元素进行归并排序
     *
     * @param begin 开始
     * @param end   结束
     */
    private void divide(int begin, int end) {
        if (end - begin < 2) return;  // size = 1,0

        int mid = (begin + end) >> 1;
        divide(begin, mid);       // 分割序列
        divide(mid, end);
        merge(begin, mid, end);   // 合并
    }

    /**
     * 合并
     * 将[begin,mid) 和 [mid,end) 范围内的元素合并成一个有序序列
     */
    private void merge(int begin, int mid, int end) {
        int l1 = 0, l2 = mid - begin;   // 左数组
        int r1 = mid;       // 右数组
        int a1 = begin;               // 覆盖的位置

        // 备份数组
        for (int i = l1; i < l2; i++) {
            leftArray[i] = array[i + begin];        // 递归的时候 要加当前数组的起始位置 begin
        }

        // 遍历备份左边的数组
        while (l1 < l2) {
            if (r1 < end && cmp(array[r1], leftArray[l1]) < 0) {
                array[a1++] = array[r1++];// a1++;r1++;
            } else {
                array[a1++] = leftArray[l1++]; //a1++;l1++;
            }
            swapCount++;
        }
    }
}
