package 数据结构和算法.算法.排序;

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
        for (int i = 0; i < 10000; i++) {
            array[i] = new Random().nextInt(ARRAY_SIZE);
        }
    }

    public static void main(String[] args) {
        Integer[] a1 = Arrays.copyOf(array, array.length);
        Integer[] a2 = Arrays.copyOf(array, array.length);
        Times.test("m1", () -> {
            m1(a1);
        });
        Times.test("m2", () -> {
            m2(a2);
        });
    }

    private static void m1(Integer[] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = i; j < array.length - 1; j++) {
                if (array[i] > array[j]) {
                    int temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                }
            }
        }
    }

    private static void m2(Integer[] array) {
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
