package 数据结构和算法.算法.排序.sort;

import java.util.Arrays;

class BubbleSortTest {

    static Integer[] leftArray;
    static Integer[] array = {
            4, 2, 1, 5, 3, 7, 6, 8, 9
    };

    public static void main(String[] args) {
        // 插入排序,用二分法找出要插入的位置
        for (int begin = 1; begin < array.length; begin++) {
            // 要插入的元素
            Integer insert = array[begin];
            // 二分法查找
            int l = 0;
            int r = begin;
            while (l < r) {
                int mid = (l + r) >> 1;
                if (array[mid] > array[begin]) {
                    r = mid;
                } else {
                    l = mid + 1;
                }
            }
            // 复制数组
            // [l,begin)
            for (int i = begin; i > l; i--) {
                array[i] = array[i - 1];
            }
            array[l] = insert;
        }
        System.out.println(Arrays.toString(array));
    }
}