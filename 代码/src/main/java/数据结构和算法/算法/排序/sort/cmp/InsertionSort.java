package 数据结构和算法.算法.排序.sort.cmp;

import 数据结构和算法.算法.排序.sort.Sort;

/**
 * @author like
 * @date 2020-12-30 8:47
 * @contactMe 980650920@qq.com
 * @description 插入排序
 */
public class InsertionSort<E extends Comparable<E>> extends Sort<E> {

    @Override
    protected E[] sort() {
        for (int begin = 1; begin < array.length; begin++) {
            E insertVal = array[begin];
            // 1.二分搜索找到插入的位置
            int l = 0;
            int r = begin;
            while (l < r) {
                int mid = (l + r) >> 1;
                if (cmp(begin, mid) < 0) {
                    r = mid;
                } else {
                    l = mid + 1;
                }
            }
            // 2.将[l,begin) 右移一位
            if (begin - l >= 0) System.arraycopy(array, l, array, l + 1, begin - l);
            // 3.插入
            array[l] = insertVal;
        }
        return array;
    }

    private E[] m1() {
        for (int start = 1; start < array.length; start++) {   // 1.假设排好序的数组是 array[0]
            int cur = start;                                  // 2.记录取出元素的index
            while (cur > 0 && cmp(cur, cur - 1) < 0) {       // 3.比较 array[cur] 是不是小于 array[cur-1]
                swap(cur, cur - 1);                         // 4.交换
                cur--;                                     // 5.继续交换 -> 直到大于
            }
        }
        return array;
    }

//    private E[] m2() {
//        for (int start = 1; start < array.length; start++) {        // 1.假设排好序的数组是 array[0]
//            int cur = start;                                       // 2.记录取出元素的index
//            E unSort = array[cur];                                // 3.记录取出未排序数组中的元素
//            while (cur > 0 && cmp(unSort, array[cur - 1]) < 0) {   // 4.比较未排序元素，和已经排序的元素
//                array[cur] = array[cur - 1];                    // 5.如果小于就一直交换，直到大于
//                cur--;
//            }
//            array[cur] = unSort;                             // 6.最终排序的位置
//        }
//        return array;
//    }

    /**
     * 搜索insertIdx处的元素在有序数组中的插入位置
     * 数组中已经排好序的数组的区间是[0,insertIdx)
     */
    private int search(int insertIdx) {
        int begin = 0;
        int end = insertIdx;
        while (begin < end) {
            int mid = (begin + end) >> 1;
            if (cmp(insertIdx, mid) < 0) {
                end = mid;
            } else {
                begin = mid + 1;
            }
        }
        return begin;
    }

}
