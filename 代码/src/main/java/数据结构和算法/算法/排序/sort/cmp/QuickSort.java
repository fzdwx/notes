package 数据结构和算法.算法.排序.sort.cmp;

import 数据结构和算法.算法.排序.sort.Sort;

/**
 * @author like
 * @date 2021-01-01 11:16
 * @contactMe 980650920@qq.com
 * @description
 */
public class QuickSort<E extends Comparable<E>> extends Sort<E> {

    @Override
    protected E[] sort() {
        sort(0, array.length);
        return array;
    }

    /**
     * 对[begin，end)范围内的元素进行快速排序
     */
    private void sort(int begin, int end) {
        if (end - begin < 2) return;

        // 确定轴点元素
        int mid = pivotIdx(begin, end);
        // 对子序列进行快速排序
        sort(begin, mid);
        sort(mid + 1, end);
    }

    /**
     * 返回[begin，end)范围内轴点元素的最终位置
     */
    private int pivotIdx(int begin, int end) {
        E e = array[begin];   // 轴点元素，备份
        end--;// 当前end是array.length,所以要--
        while (begin < end) {
            // 左右交替
            while (begin < end) {
                if (cmp(e, array[end]) < 0) {  // 大于轴点的放右边（end）
                    end--;
                } else {   // 小于轴点的放左边（左边是begin）
                    array[begin++] = array[end];
                    this.swapCount++;
                    break;
                }
            }
            // 左右交替
            while (begin < end) {
                if (cmp(e, array[begin]) > 0) { //  小于轴点的放左边
                    begin++;
                } else {    // 大于的放右边
                    array[end--] = array[begin];
                    this.swapCount++;
                    break;
                }
            }
        }
        // 放入轴点元素
        array[begin] = e;
        return begin;
    }
}
