package 数据结构和算法.算法.排序.sort.nocmp;

import 数据结构和算法.算法.排序.sort.Sort;

/**
 * @author like
 * @date 2021-01-03 10:30
 * @contactMe 980650920@qq.com
 * @description 基数排序
 */
public class CountingSort extends Sort<Integer> {

    @Override
    protected Integer[] sort() {
        // 1.找出最大值
        int max = 0;
        for (Integer i : array) {
            if (i > max) {
                max = i;
            }
        }
        // 2.写一个序列，保存0-max的所有值
        int[] counts = new int[max + 1];
        for (Integer i : array) {
            counts[i]++;
        }
        // 3.给数组重新赋值
        int idx = 0;
        for (int i = 0; i < counts.length; i++) {
            while (counts[i]-- != 0) {
                array[idx++] = i;
            }
        }
        return array;
    }
}
