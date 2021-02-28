package 算法.排序.sort.nocmp;

import 算法.排序.sort.Sort;

/**
 * @author like
 * @date 2021-01-03 12:14
 * @contactMe 980650920@qq.com
 * @description
 */
public class RadixSort extends Sort<Integer> {


    @Override
    protected Integer[] sort() {
        int max = array[0];
        for (Integer integer : array) {
            if (integer > max) {
                max = integer;
            }
        }
        for (int i = 0; i <= max; i *= 10) {
            countSort(i);
        }
        return null;
    }

    private void countSort(int d) {
        // 2.写一个序列，保存0-max的所有值
        int[] counts = new int[10];
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
    }
}
