package 算法.排序.sort.nocmp;

import 算法.排序.sort.Sort;

/**
 * @author like
 * @date 2021-01-03 10:30
 * @contactMe 980650920@qq.com
 * @description 基数排序
 */
public class CountingSort extends Sort<Integer> {

    protected Integer[] sort0() {
        // 1.找出最值
        int max = array[0];
        int min = array[0];
        for (Integer i : array) {
            if (i > max) {
                max = i;
            }
            if (i < min) {
                min = i;
            }
        }
        // 2.写一个序列,存储array中元素出现的次数
        int[] counts = new int[max - min + 1];
        // 3.统计出现的次数
        for (int i = 0; i < counts.length; i++) {
            counts[array[i] - min]++;
        }
        // 4.累加次数
        for (int i = 1; i < counts.length; i++) {
            counts[i] += counts[i - 1];
        }
        // 5.从后往前遍历约束,将他放入有序数组有合适的位置
        int[] newArr = new int[array.length];
        for (int i = array.length - 1; i >= 0; i--) {
            //--counts[array[i] -min]
            newArr[--counts[array[i] - min]] = array[i];
        }
        // 6.将有序数组赋值到array
        for (int i = 0; i < newArr.length; i++) {
            array[i] = newArr[i];
        }
        return array;
    }

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
