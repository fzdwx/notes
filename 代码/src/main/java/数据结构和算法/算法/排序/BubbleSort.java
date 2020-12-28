package 数据结构和算法.算法.排序;

import 数据结构和算法.算法.排序.tools.Asserts;
import 数据结构和算法.算法.排序.tools.Times;

import java.util.Arrays;
import java.util.Random;

/**
 * @author like
 * @date 2020-12-28 8:40
 * @contactMe 980650920@qq.com
 * @description 冒泡排序
 */
public class BubbleSort {
    public static final Integer ARRAY_SIZE = 10000;
    static Integer[] array;

    static {
        array = new Integer[ARRAY_SIZE];
        for (int i = 0; i < ARRAY_SIZE; i++) {
            array[i] = new Random().nextInt(ARRAY_SIZE);
        }
    }

    public static void main(String[] args) {
        Integer[] a = Arrays.copyOf(array, array.length);
        Arrays.sort(array);
        Times.test("m-初始", () -> {
            m(a);
        });
        Asserts.test(Arrays.equals(a, array));
    }

    private static void m(Integer[] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = i; j < array.length; j++) {
                if (array[i] > array[j]) {
                    int temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                }
            }
        }
    }

    private static void m1(Integer[] array) {
        for (int end = array.length - 1; end > 0; end--) {
            int index = 1;
            for (int begin = 1; begin <= end; begin++) {
                if (array[begin] > array[begin - 1]) {
                    int temp = array[begin];
                    array[begin] = array[begin - 1];
                    array[begin - 1] = temp;
                    index = begin;
                }
            }
            end = index;
        }
    }

    private static void m2(Integer[] array) {
        for (int i = 0; i < array.length; i++) {
            boolean sorted = true;
            for (int j = i; j < array.length - 1; j++) {
                if (array[i] > array[j]) {
                    int temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                    sorted = false;
                }
            }
            if (sorted) break;
        }
    }

    /**
     * 学习网站上面的解法
     *
     * @param array 数组
     */
    private static void s(Integer[] array) {
        boolean flag;
        do {
            flag = false;
            for (int i = 1; i < array.length; i++) {
                if (array[i] < array[i - 1]) {
                    int temp = array[i];
                    array[i] = array[i - 1];
                    array[i - 1] = temp;
                    flag = true;
                }

            }
        } while (flag);
    }
}
